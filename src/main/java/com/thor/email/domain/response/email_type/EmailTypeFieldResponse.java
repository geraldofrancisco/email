package com.thor.email.domain.response.email_type;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELD_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldResponse {

  @Schema(description = EMAIL_TYPE_FIELD_NAME_DESCRIPTION)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION)
  private boolean required;
}
