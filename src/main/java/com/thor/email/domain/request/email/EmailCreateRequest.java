package com.thor.email.domain.request.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_EMAIL_TYPE_ID_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_REQUIRED;

import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValidObjectId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({EmailCreateRequest.class, SecondValidationGroup.class})
public class EmailCreateRequest {

  @Schema(description = EMAIL_CONTROLLER_REQUEST_EMAIL_TYPE_ID_DESCRIPTION)
  @NotBlank(message = EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_REQUIRED)
  @ValidObjectId(message = "", groups = SecondValidationGroup.class)
  private String emailTypeId;

  @Schema(description = EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_DESCRIPTION)
  private HashSet<@Valid EmailCreateFieldsValueRequest> fieldValues;

}
