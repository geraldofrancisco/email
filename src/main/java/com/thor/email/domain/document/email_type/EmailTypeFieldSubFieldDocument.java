package com.thor.email.domain.document.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELD_NAME_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELD_REQUIRED_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELD_VALUE_TYPE_FIELD;

import com.thor.email.domain.enums.FieldTypeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldSubFieldDocument {

  @Field(EMAIL_TYPE_FIELD_SUBFIELD_NAME_FIELD)
  private String name;

  @Field(EMAIL_TYPE_FIELD_SUBFIELD_REQUIRED_FIELD)
  private boolean required;

  @Field(EMAIL_TYPE_FIELD_SUBFIELD_VALUE_TYPE_FIELD)
  private FieldTypeFormat valueType;
}
