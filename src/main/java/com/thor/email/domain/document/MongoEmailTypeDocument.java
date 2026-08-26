package com.thor.email.domain.document;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_BODY_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_CREATION_DATETIME_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_FIELDS_FIELD;
import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_TABLE_NAME;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = EMAIL_TYPE_TABLE_NAME)
public class MongoEmailTypeDocument {

  @Id
  private ObjectId id;

  @Field(name = EMAIL_TYPE_CREATION_DATETIME_FIELD)
  private LocalDateTime timestampCreatedDate;

  @Field(EMAIL_TYPE_BODY_FIELD)
  private String body;

  @Field(EMAIL_TYPE_FIELDS_FIELD)
  private List<MongoEmailTypeFieldDocument> fields;
}
