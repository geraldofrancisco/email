package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_BODY_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_BODY_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_FIELDS_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.INTERPOLATE_VARIABLE_IN_HTML;

import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValidHTML;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({EmailTypeRequest.class, SecondValidationGroup.class})
public class EmailTypeRequest {

  @Schema(description = EMAIL_TYPE_REQUEST_BODY_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_REQUEST_BODY_REQUIRED)
  @ValidHTML
  private String body;

  @Schema(description = EMAIL_TYPE_REQUEST_FIELDS_DESCRIPTION)
  @Builder.Default
  @Valid
  private List<EmailTypeFieldRequest> fields = new ArrayList<>();

  @Hidden
  @AssertTrue(message = EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS, groups = SecondValidationGroup.class)
  public boolean isExistsFieldsInBody() {
    if (fields == null || fields.isEmpty()) {
      return true;
    }

    return fields.parallelStream()
        .allMatch(
            field -> body.contains(String.format(INTERPOLATE_VARIABLE_IN_HTML, field.getName())));
  }
}
