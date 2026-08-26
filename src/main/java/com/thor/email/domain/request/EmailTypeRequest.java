package com.thor.email.domain.request;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_REQUEST_BODY_REQUIRED;

import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValidHTML;
import io.swagger.v3.oas.annotations.Hidden;
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

  @NotEmpty(message = EMAIL_TYPE_REQUEST_BODY_REQUIRED)
  @ValidHTML
  private String body;

  @Builder.Default
  @Valid
  private List<EmailTypeFieldRequest> fields = new ArrayList<>();

  @Hidden
  @AssertTrue(message = EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS, groups = SecondValidationGroup.class)
  public boolean isExistsFieldsInBody() {
    if (fields == null || fields.isEmpty()) {
      return true;
    }

    return fields.stream()
        .allMatch(field -> body.contains(String.format("@@%s@@", field.getName())));
  }
}
