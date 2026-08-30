package com.thor.email.domain.response.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL__BODY_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__BCC_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__TIMESTAMP_CREATED_DATE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__EMAIL_TYPE_ID_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__TIMESTAMP_SEND_DATE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__TITLE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL__TO_DESCRIPTION;
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
public class EmailResponse {

  @Schema(description = UNIQUE_IDENTIFIER_DESCRIPTION)
  private String id;

  @Schema(description = EMAIL__EMAIL_TYPE_ID_DESCRIPTION)
  private String emailTypeId;

  @Schema(description = EMAIL__TIMESTAMP_CREATED_DATE_DESCRIPTION)
  @JsonFormat(pattern = DATE_TIME_PATTERN)
  private LocalDateTime timestampCreatedDate;

  @Schema(description = EMAIL__TITLE_DESCRIPTION)
  private String title;

  @Schema(description = EMAIL__BODY_DESCRIPTION)
  private String body;

  @Schema(description = EMAIL__TO_DESCRIPTION)
  private List<String> to;

  @Schema(description = EMAIL__BCC_DESCRIPTION)
  private List<String> bcc;

  @Schema(description = EMAIL__TIMESTAMP_SEND_DATE_DESCRIPTION)
  @JsonFormat(pattern = DATE_TIME_PATTERN)
  private LocalDateTime timestampSendDate;
}
