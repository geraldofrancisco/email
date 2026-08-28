package com.thor.email.domain.request.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_EMAIL_TYPE_ID_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCreateRequest {

  @Schema(description = EMAIL_CONTROLLER_REQUEST_EMAIL_TYPE_ID_DESCRIPTION)
  @NotBlank(message = EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_REQUIRED)
  private String emailTypeId;

  @Schema(description = EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_DESCRIPTION)
  @Valid
  private List<EmailCreateFieldsValueRequest> fieldValues;

}
