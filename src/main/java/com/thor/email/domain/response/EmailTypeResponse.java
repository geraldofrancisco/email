package com.thor.email.domain.response;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_ID;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_NAME;
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
public class EmailTypeResponse {

  @Schema(description = EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_ID, example = MONGO_ID_EXAMPLE)
  private String id;

  @Schema(description = EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_NAME)
  private String name;
}
