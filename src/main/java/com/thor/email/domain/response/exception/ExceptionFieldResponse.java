package com.thor.email.domain.response.exception;

import static com.thor.email.domain.constants.ProjectConstants.PROJECT_EXCEPTION_FIELD_RESPONSE_MESSAGE_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.PROJECT_EXCEPTION_FIELD_RESPONSE_NAME_DESCRIPTION;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionFieldResponse {

  @Schema(description = PROJECT_EXCEPTION_FIELD_RESPONSE_NAME_DESCRIPTION)
  private String name;

  @Schema(description = PROJECT_EXCEPTION_FIELD_RESPONSE_MESSAGE_DESCRIPTION)
  private String message;
}
