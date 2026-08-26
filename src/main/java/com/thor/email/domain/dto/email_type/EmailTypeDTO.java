package com.thor.email.domain.dto.email_type;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeDTO {
  private ObjectId id;

  @Builder.Default
  private LocalDateTime timestampCreatedDate = LocalDateTime.now();

  private String body;

  private List<EmailTypeFieldDTO> fields;
}
