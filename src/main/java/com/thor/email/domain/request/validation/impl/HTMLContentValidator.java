package com.thor.email.domain.request.validation.impl;

import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderDeclarationList;
import com.thor.email.domain.request.validation.ValidHTML;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HTMLContentValidator implements ConstraintValidator<ValidHTML, String> {

  // Tags auto-contidas (void tags) do HTML5 que não exigem fechamento </tag> nem <tag/>
  private static final Set<Set<String>> VOID_TAGS = Set.of(
      Set.of("area", "base", "br", "col", "embed", "hr", "img", "input"),
      Set.of("link", "meta", "param", "source", "track", "wbr")
  );

  private static final Set<String> HTML5_VOID_TAGS = new HashSet<>();

  static {
    VOID_TAGS.forEach(HTML5_VOID_TAGS::addAll);
  }

  @Override
  public boolean isValid(String html, ConstraintValidatorContext context) {
    if (html == null || html.trim().isEmpty()) {
      return true;
    }

    // 1. Validar aninhamento de tags respeitando regras do HTML5
    Optional<String> syntaxError = validateHtmlTagBalancing(html);
    if (syntaxError.isPresent()) {
      buildCustomMessage(context, syntaxError.get());
      return false;
    }

    // 2. Parse Jsoup para extração do CSS
    Document doc = Jsoup.parse(html);

    // 3. Validar blocos <style>
    Optional<String> errorCssBlock = validateCssBlocks(doc);
    if (errorCssBlock.isPresent()) {
      buildCustomMessage(context, errorCssBlock.get());
      return false;
    }

    // 4. Validar atributos style="..." (CSS Inline)
    Optional<String> errorInlineCss = validateInlineCss(doc);
    if (errorInlineCss.isPresent()) {
      buildCustomMessage(context, errorInlineCss.get());
      return false;
    }

    return true;
  }

  private Optional<String> validateHtmlTagBalancing(String html) {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();

      // Desabilita validações de DTD/Schema externas por segurança (XXE Protection)
      factory.setFeature("http://xml.org/sax/features/namespaces", false);
      factory.setFeature("http://xml.org/sax/features/validation", false);
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

      SAXParser saxParser = factory.newSAXParser();

      HtmlTagBalanceHandler handler = new HtmlTagBalanceHandler();
      saxParser.parse(new InputSource(new StringReader(html)), handler);

      return handler.getUnclosedTagError();
    } catch (SAXException e) {
      // Se for nossa própria exceção de tag desalinhada
      if (e.getCause() instanceof HtmlValidationException) {
        return Optional.of(e.getCause().getMessage());
      }
      return Optional.of("Erro de sintaxe no HTML: " + e.getMessage());
    } catch (Exception e) {
      return Optional.of("Erro ao processar a estrutura do HTML: " + e.getMessage());
    }
  }

  private Optional<String> validateCssBlocks(Document doc) {
    var styles = doc.select("style");

    return IntStream.range(0, styles.size())
        .filter(i -> {
          String cssContent = styles.get(i).data().trim();
          return !cssContent.isEmpty()
              && CSSReader.readFromString(cssContent, ECSSVersion.CSS30) == null;
        })
        .mapToObj(i -> String.format("Erro de sintaxe no bloco <style> [%d]", i + 1))
        .findFirst();
  }

  private Optional<String> validateInlineCss(Document doc) {
    var elementsWithStyle = doc.select("[style]");

    for (Element el : elementsWithStyle) {
      String styleAttr = el.attr("style").trim();
      if (!styleAttr.isEmpty() && !isInlineCSSValid(styleAttr)) {
        return Optional.of(
            String.format("Erro de sintaxe no atributo style da tag <%s>", el.tagName()));
      }
    }
    return Optional.empty();
  }

  private boolean isInlineCSSValid(String inlineCss) {
    return CSSReaderDeclarationList.readFromString(inlineCss, ECSSVersion.CSS30) != null;
  }

  private void buildCustomMessage(ConstraintValidatorContext context, String errorMessage) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage)
        .addConstraintViolation();
  }

  // --- SAX Handler customizado para validar pilha de tags HTML ---
  private static class HtmlTagBalanceHandler extends DefaultHandler {

    private final Deque<String> tagStack = new ArrayDeque<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      String tagName = qName.toLowerCase();

      // Ignora tags void do HTML5 (ex: meta, br, hr, img)
      if (!HTML5_VOID_TAGS.contains(tagName)) {
        tagStack.push(tagName);
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      String tagName = qName.toLowerCase();

      // Tags void não têm correspondente de fechamento na pilha
      if (HTML5_VOID_TAGS.contains(tagName)) {
        return;
      }

      if (tagStack.isEmpty()) {
        throw SAXExceptionWithCause(
            String.format("Erro no HTML: A tag </%s> foi fechada sem ter sido aberta.", tagName));
      }

      String lastOpenedTag = tagStack.pop();
      if (!lastOpenedTag.equals(tagName)) {
        throw SAXExceptionWithCause(String.format(
            "Erro no HTML: A tag <%s> não foi fechada corretamente antes do fechamento de </%s>.",
            lastOpenedTag, tagName));
      }
    }

    public Optional<String> getUnclosedTagError() {
      if (!tagStack.isEmpty()) {
        String unclosedTag = tagStack.peek();
        return Optional.of(
            String.format("Erro no HTML: A tag <%s> não foi fechada corretamente.", unclosedTag));
      }
      return Optional.empty();
    }

    private SAXException SAXExceptionWithCause(String message) {
      SAXException saxException = new SAXException(message);
      saxException.initCause(new HtmlValidationException(message));
      return saxException;
    }
  }

  private static class HtmlValidationException extends RuntimeException {

    public HtmlValidationException(String message) {
      super(message);
    }
  }
}