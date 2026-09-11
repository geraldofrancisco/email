package com.thor.email.domain.document.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_NAME_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUIRED_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_SUBFIELDS_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_TYPE_FIELD;

import com.thor.email.domain.enums.FieldType;
import java.util.List;
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

  @Field(EMAIL_TYPE_FIELD_TYPE_FIELD)
  private FieldType type;

  @Field(EMAIL_TYPE_FIELD_SUBFIELDS_FIELD)
  private List<EmailTypeFieldSubFieldDocument> subFields;
}
