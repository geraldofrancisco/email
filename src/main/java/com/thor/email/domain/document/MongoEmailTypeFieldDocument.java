package com.thor.email.domain.document;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELD_NAME_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELD_REQUIRED_FIELD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongoEmailTypeFieldDocument {

  @Field(EMAIL_TYPE_FIELD_NAME_FIELD)
  private String name;

  @Field(EMAIL_TYPE_FIELD_REQUIRED_FIELD)
  private boolean required;
}
