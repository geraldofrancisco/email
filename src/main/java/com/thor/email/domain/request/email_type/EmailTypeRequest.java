package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_BODY_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELDS_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_REQUEST_BODY_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_REQUEST_NAME_REQUIRED;
import static com.thor.email.domain.constants.ProjectConstants.THYMELEAF_LIST_IN_HTML;
import static com.thor.email.domain.constants.ProjectConstants.THYMELEAF_VARIABLE_IN_HTML;

import com.thor.email.domain.exception.FieldType;
import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValidHTML;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({EmailTypeRequest.class, SecondValidationGroup.class})
public class EmailTypeRequest {

  @Schema(description = EMAIL_TYPE_BODY_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_REQUEST_BODY_REQUIRED)
  @ValidHTML
  private String body;

  @Schema(description = EMAIL_TYPE_NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_REQUEST_NAME_REQUIRED)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELDS_DESCRIPTION)
  private HashSet<@Valid EmailTypeFieldRequest> fields = new HashSet<>();

  @Hidden
  @AssertTrue(message = EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS, groups = SecondValidationGroup.class)
  public boolean isExistsFieldsInBody() {
    if (fields == null || fields.isEmpty()) {
      return true;
    }

    return fields.parallelStream().allMatch(this::validateFieldInBody);
  }

  private boolean validateFieldInBody(EmailTypeFieldRequest field) {
    if (field.getType() == FieldType.LIST) {
      // 1. Valida se a coleção iterável existe no Thymeleaf (${items})
      String listPattern = String.format(THYMELEAF_VARIABLE_IN_HTML, field.getName());
      boolean hasListInBody = body.contains(THYMELEAF_LIST_IN_HTML) && body.contains(listPattern);

      if (!hasListInBody) {
        return false;
      }

      // 2. Valida se todos os subcampos do item existem dentro do template HTML
      return field.getSubFields().stream().allMatch(subField -> {
        String propertyAccess = String.format(".%s", subField.getName());
        String selectionVariable = String.format("*{%s}", subField.getName());

        return body.contains(propertyAccess) || body.contains(selectionVariable);
      });
    }

    // Validação padrão para variáveis simples (${userName})
    return body.contains(String.format(THYMELEAF_VARIABLE_IN_HTML, field.getName()));
  }
}
