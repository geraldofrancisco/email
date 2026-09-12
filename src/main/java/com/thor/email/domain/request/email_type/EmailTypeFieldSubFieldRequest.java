package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_FIELDS_SUBFIELDS__NAME_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_FIELDS_SUBFIELDS__VALUE_TYPE_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELDS__NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELDS__VALUE_TYPE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__VALUE_TYPE_INVALID;

import com.thor.email.domain.enums.FieldTypeFormat;
import com.thor.email.domain.request.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "required")
public class EmailTypeFieldSubFieldRequest {

  @Schema(description = EMAIL_TYPE_FIELD_SUBFIELDS__NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_FIELD_FIELDS_SUBFIELDS__NAME_REQUIRED)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELD_SUBFIELDS__VALUE_TYPE_DESCRIPTION, implementation = FieldTypeFormat.class)
  @NotBlank(message = EMAIL_TYPE_FIELD_FIELDS_SUBFIELDS__VALUE_TYPE_REQUIRED)
  @ValueOfEnum(enumClass = FieldTypeFormat.class, message = EMAIL_TYPE_FIELD__VALUE_TYPE_INVALID)
  private String valueType;

  @Schema(description = EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION)
  private boolean required = true;

}
