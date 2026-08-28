package com.thor.email.domain.document.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_BODY_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CCO_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATION_DATETIME_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TABLE_NAME;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TIMESTAMP_SEND_DATE_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TITLE_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TO_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TYPE_ID;

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
@Document(collection = EMAIL_TABLE_NAME)
public class EmailDocument {
  @Id
  private ObjectId id;

  @Field(name = EMAIL_TYPE_ID)
  private ObjectId emailTypeId;

  @Field(name = EMAIL_CREATION_DATETIME_FIELD)
  private LocalDateTime timestampCreatedDate;

  @Field(name = EMAIL_TITLE_FIELD)
  private String title;

  @Field(name = EMAIL_BODY_FIELD)
  private String body;

  @Field(name = EMAIL_TO_FIELD)
  private List<String> to;

  @Field(name = EMAIL_CCO_FIELD)
  private List<String> cco;

  @Field(name = EMAIL_TIMESTAMP_SEND_DATE_FIELD)
  private LocalDateTime timestampSendDate;
}
