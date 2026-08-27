package com.thor.email.domain.dto.email_type;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeDTO {

  @Id
  @Builder.Default
  private ObjectId id = new ObjectId();

  @Builder.Default
  private LocalDateTime timestampCreatedDate = LocalDateTime.now();

  private String name;

  private String body;

  private List<EmailTypeFieldDTO> fields;
}
