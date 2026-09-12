package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__SUBFIELDS_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__SUBFIELDS_NOT_EMPTY;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__SUBFIELDS_NOT_TO_BE_FILLED_IN;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__TYPE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__TYPE_INVALID;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__TYPE_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__VALUE_TYPE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__VALUE_TYPE_INVALID;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD__VALUE_TYPE_REQUIRED;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thor.email.domain.enums.FieldType;
import com.thor.email.domain.enums.FieldTypeFormat;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValueOfEnum;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
@GroupSequence({EmailTypeFieldRequest.class, SecondValidationGroup.class})
public class EmailTypeFieldRequest {

  @Schema(description = EMAIL_TYPE_FIELD_NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION)
  private boolean required = true;

  @Schema(description = EMAIL_TYPE_FIELD__TYPE_DESCRIPTION, implementation = FieldType.class)
  @NotBlank(message = EMAIL_TYPE_FIELD__TYPE_REQUIRED)
  @ValueOfEnum(enumClass = FieldType.class, message = EMAIL_TYPE_FIELD__TYPE_INVALID)
  private String type;

  @Schema(description = EMAIL_TYPE_FIELD__SUBFIELDS_DESCRIPTION)
  private Set<@Valid EmailTypeFieldSubFieldRequest> subFields = new HashSet<>();

  @Schema(description = EMAIL_TYPE_FIELD__VALUE_TYPE_DESCRIPTION, implementation = FieldTypeFormat.class)
  @ValueOfEnum(enumClass = FieldTypeFormat.class, message = EMAIL_TYPE_FIELD__VALUE_TYPE_INVALID)
  private String valueType;

  @Hidden
  @JsonIgnore
  @AssertTrue(message = EMAIL_TYPE_FIELD__SUBFIELDS_NOT_EMPTY, groups = SecondValidationGroup.class)
  public boolean isHasSubFieldsIfList() {
    if (FieldType.LIST.name().equals(type)) {
      return Objects.nonNull(subFields) && !subFields.isEmpty();
    }
    return true;
  }

  @Hidden
  @JsonIgnore
  @AssertTrue(message = EMAIL_TYPE_FIELD__VALUE_TYPE_REQUIRED, groups = SecondValidationGroup.class)
  public boolean isMustBeFilledInIfTheTypeIsSimple() {
    return !FieldType.SIMPLE.name().equals(type) || StringUtils.isNotBlank(valueType);
  }

  @Hidden
  @JsonIgnore
  @AssertTrue(message = EMAIL_TYPE_FIELD__SUBFIELDS_NOT_TO_BE_FILLED_IN, groups = SecondValidationGroup.class)
  public boolean isMustBeNullIfTheTypeIsSimple() {
    return !FieldType.SIMPLE.name().equals(type) || Objects.isNull(subFields) || subFields.isEmpty();
  }
}
