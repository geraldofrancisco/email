package com.thor.email.domain.response.email_type;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_BODY_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELDS_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.DATE_TIME_CREATION_REGISTER_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.DATE_TIME_PATTERN;
import static com.thor.email.domain.constants.ProjectConstants.UNIQUE_IDENTIFIER_DESCRIPTION;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeResponse {

  @Schema(description = UNIQUE_IDENTIFIER_DESCRIPTION)
  private String id;

  @Schema(description = DATE_TIME_CREATION_REGISTER_DESCRIPTION)
  @JsonFormat(pattern = DATE_TIME_PATTERN)
  private LocalDateTime timestampCreatedDate;

  @Schema(description = EMAIL_TYPE_BODY_DESCRIPTION)
  private String body;

  @Schema(description = EMAIL_TYPE_NAME_DESCRIPTION)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELDS_DESCRIPTION)
  private List<EmailTypeResponse> fields;
}
