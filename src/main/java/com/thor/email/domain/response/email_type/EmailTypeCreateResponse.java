package com.thor.email.domain.response.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_ID;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_NAME;
import static com.thor.email.domain.constants.ProjectConstants.MONGO_ID_EXAMPLE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeCreateResponse {

  @Schema(description = EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_ID, example = MONGO_ID_EXAMPLE)
  private String id;

  @Schema(description = EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_NAME)
  private String name;
}
