package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_BODY_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_REQUEST_BODY_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_REQUEST_NAME_REQUIRED;

import com.thor.email.domain.request.validation.ValidHTML;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeRequest {

  @Schema(description = EMAIL_TYPE_BODY_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_REQUEST_BODY_REQUIRED)
  @ValidHTML
  private String body;

  @Schema(description = EMAIL_TYPE_NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_REQUEST_NAME_REQUIRED)
  private String name;

}
