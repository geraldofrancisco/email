package com.thor.email.domain.response.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_CREATE_RESPONSE_BODY;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_CREATE_RESPONSE_ID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCreateResponse {

  @Schema(description = EMAIL_CONTROLLER_CREATE_RESPONSE_ID)
  private String id;

  @Schema(description = EMAIL_CONTROLLER_CREATE_RESPONSE_BODY)
  private String body;
}
