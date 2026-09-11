package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thor.email.domain.exception.FieldType;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "required")
public class EmailTypeFieldSubFieldRequest {

  @Schema(description = EMAIL_TYPE_FIELD_NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION)
  private boolean required = false;


}
