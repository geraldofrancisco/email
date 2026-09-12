package com.thor.email.domain.request.validation.impl;

import com.thor.email.domain.enums.FieldType;
import com.thor.email.domain.request.email_type.EmailTypeFieldRequest;
import com.thor.email.domain.request.email_type.EmailTypeFieldSubFieldRequest;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.request.validation.ValidEmailTypeFieldsInBody;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ValidEmailTypeFieldsInBodyValidator implements
    ConstraintValidator<ValidEmailTypeFieldsInBody, EmailTypeRequest> {

  @Override
  public boolean isValid(EmailTypeRequest request, ConstraintValidatorContext context) {
    if (request == null || request.getFields() == null || request.getFields().isEmpty()) {
      return true;
    }

    String body = request.getBody();
    if (body == null || body.isBlank()) {
      return true;
    }

    boolean isValid = true;

    for (EmailTypeFieldRequest field : request.getFields()) {
      if (field.getName() == null || field.getName().isBlank()) {
        continue;
      }

      if (FieldType.LIST.name().equals(field.getType())) {

        // 1. Identifica o nome do objeto da iteração (ex: extrai 'item' de 'th:each="item : ${lista}"')
        String itemAlias = extractItemAliasFromThEach(body, field.getName());

        if (itemAlias != null) {
          // 2. Extrai todas as propriedades do 'item' usadas no HTML (ex: 'dataHora', 'nome', 'valor')
          Set<String> htmlSubFields = extractSubFieldsFromHtml(body, itemAlias);

          // 3. Mapeia os nomes dos subcampos enviados no payload DTO
          Set<String> dtoSubFields = field.getSubFields() != null
              ? field.getSubFields().stream()
              .map(EmailTypeFieldSubFieldRequest::getName)
              .filter(Objects::nonNull)
              .collect(Collectors.toSet())
              : new HashSet<>();

          // 4. Identifica subcampos que estão no HTML, mas FALTAM no DTO
          for (String htmlSubField : htmlSubFields) {
            if (!dtoSubFields.contains(htmlSubField)) {
              buildCustomViolation(
                  context,
                  String.format("O subcampo '%s' está presente no template HTML na lista '%s', mas não foi informado nas requisições do 'subFields'.", htmlSubField, field.getName()),
                  "fields"
              );
              isValid = false;
            }
          }
        } else {
          // Validação caso a lista nem exista no th:each do HTML
          String listPattern = String.format("${%s}", field.getName());
          if (!body.contains(listPattern)) {
            buildCustomViolation(
                context,
                String.format("A lista '%s' não foi encontrada no template HTML.", field.getName()),
                "fields"
            );
            isValid = false;
          }
        }

      } else {
        // Validação simples para variáveis comuns
        String variablePattern = String.format("${%s}", field.getName());
        if (!body.contains(variablePattern)) {
          buildCustomViolation(
              context,
              String.format("O campo '%s' não foi encontrado no template HTML.", field.getName()),
              "fields"
          );
          isValid = false;
        }
      }
    }

    return isValid;
  }

  private String extractItemAliasFromThEach(String html, String listName) {
    String regex = "th:each=\"\\s*([a-zA-Z0-9_]+)\\s*:\\s*\\$\\{" + Pattern.quote(listName) + "\\}\"";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }

  private Set<String> extractSubFieldsFromHtml(String html, String itemAlias) {
    Set<String> subFields = new HashSet<>();

    // Expressão regular para capturar chamadas do tipo: item.nomePropriedade
    String regex = Pattern.quote(itemAlias) + "\\.([a-zA-Z0-9_]+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(html);

    while (matcher.find()) {
      subFields.add(matcher.group(1));
    }

    return subFields;
  }

  private void buildCustomViolation(ConstraintValidatorContext context, String message, String propertyNode) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message)
        .addPropertyNode(propertyNode)
        .addConstraintViolation();
  }
}
