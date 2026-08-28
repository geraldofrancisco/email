package com.thor.email.domain.request.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_FIELD_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_VALUE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE__REQUEST_FIELD_VALUES_FIELD_REQUIRED;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE__REQUEST_FIELD_VALUES_VALUE_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "value")
public class EmailCreateFieldsValueRequest {

  @Schema(description = EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_FIELD_DESCRIPTION)
  @NotBlank(message = EMAIL_CREATE__REQUEST_FIELD_VALUES_FIELD_REQUIRED)
  private String field;

  @Schema(description = EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_VALUE_DESCRIPTION)
  @NotBlank(message = EMAIL_CREATE__REQUEST_FIELD_VALUES_VALUE_REQUIRED)
  private String value;
}
