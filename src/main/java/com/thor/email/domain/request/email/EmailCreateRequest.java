package com.thor.email.domain.request.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL__BODY_DESCRIPTION;

import com.thor.email.domain.request.validation.ValidHTML;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCreateRequest {

  @Schema(description = EMAIL__BODY_DESCRIPTION)
  @ValidHTML
  private String body;


}
