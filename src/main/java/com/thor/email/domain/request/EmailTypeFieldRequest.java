package com.thor.email.domain.request;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldRequest {

  @NotEmpty(message = EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED)
  private String name;

  @Builder.Default
  private Boolean required = false;
}
