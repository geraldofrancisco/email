package com.thor.email.domain.document.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_NAME_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUIRED_FIELD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldDocument {

  @Field(EMAIL_TYPE_FIELD_NAME_FIELD)
  private String name;

  @Field(EMAIL_TYPE_FIELD_REQUIRED_FIELD)
  private boolean required;
}
