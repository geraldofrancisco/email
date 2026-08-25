package com.thor.email.adapters.in.rest.validation.impl;

import static org.jsoup.parser.Parser.htmlParser;

import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReader;
import com.thor.email.adapters.in.rest.validation.ValidHTML;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Parser;

public class HTMLContentValidator implements ConstraintValidator<ValidHTML, String> {

  @Override
  public boolean isValid(String html, ConstraintValidatorContext context) {
    if (html == null || html.trim().isEmpty()) {
      return true;
    }

    // 1. Validar sintaxe do HTML
    var htmlParser = htmlParser();
    htmlParser.setTrackErrors(10);
    Document doc = Jsoup.parse(html, "", htmlParser);

    if (!htmlParser.getErrors().isEmpty()) {
      String errorHtml = htmlParser.getErrors().getFirst().getErrorMessage();
      buildCustomMessage(context, "Erro de sintaxe no HTML: " + errorHtml);
      return false;
    }

    // 2. Validar blocos <style> (CSS) via Stream
    Optional<String> errorCss = validateCss(doc);
    if (errorCss.isPresent()) {
      buildCustomMessage(context, errorCss.get());
      return false;
    }

    // 3. Validar blocos <script> (JavaScript) via Stream
    Optional<String> errorJs = validateJS(doc);
    if (errorJs.isPresent()) {
      buildCustomMessage(context, errorJs.get());
      return false;
    }

    return true;
  }

  private Optional<String> validateCss(Document doc) {
    var styles = doc.select("style");

    return IntStream.range(0, styles.size())
        .filter(i -> {
          String cssContent = styles.get(i).data().trim();
          return !cssContent.isEmpty() && !isCSSValid(cssContent);
        })
        .mapToObj(i -> String.format("Erro de sintaxe no bloco <style> [%d]", i + 1))
        .findFirst();
  }

  private Optional<String> validateJS(Document doc) {
    var scripts = doc.select("script");

    return IntStream.range(0, scripts.size())
        .filter(i -> isScriptJSExecutable(scripts.get(i)))
        .mapToObj(i -> {
          String jsContent = scripts.get(i).data().trim();
          String errorJs = getErrorJS(jsContent);
          return errorJs != null
              ? String.format("Erro de sintaxe no bloco <script> [%d]: %s", i + 1, errorJs)
              : null;
        })
        .filter(Objects::nonNull)
        .findFirst();
  }

  private boolean isScriptJSExecutable(Element script) {
    String type = script.attr("type");
    boolean isJS =
        type.isEmpty() || type.equalsIgnoreCase("text/javascript") || type.equalsIgnoreCase(
            "module");
    return isJS && !script.hasAttr("src") && !script.data().trim().isEmpty();
  }

  private boolean isCSSValid(String cssContent) {
    return CSSReader.readFromString(cssContent, ECSSVersion.CSS30) != null;
  }

  private String getErrorJS(String jsContent) {
    try {
      CompilerEnvirons env = new CompilerEnvirons();
      env.setIdeMode(true);

      ErrorReporter errorReporter = new ErrorReporter() {
        @Override
        public void warning(String m, String s, int l, String lc, int linep) {
        }

        @Override
        public void error(String m, String s, int l, String lc, int linep) {
          throw new EvaluatorException(m);
        }

        @Override
        public EvaluatorException runtimeError(String m, String s, int l, String lc, int linep) {
          return new EvaluatorException(m);
        }
      };

      Parser jsParser = new Parser(env, errorReporter);
      jsParser.parse(jsContent, null, 1);
      return null;
    } catch (EvaluatorException e) {
      return e.getMessage();
    }
  }

  private void buildCustomMessage(ConstraintValidatorContext context, String errorMessage) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage)
        .addConstraintViolation();
  }
}