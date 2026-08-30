package com.thor.email.domain.dto.email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

  @Builder.Default
  private ObjectId id = new ObjectId();

  private ObjectId typeId;

  @Builder.Default
  private LocalDateTime timestampCreatedDate = LocalDateTime.now();

  private String title;

  protected String body;

  @Builder.Default
  private List<String> to = new ArrayList<>();

  @Builder.Default
  private List<String> bcc = new ArrayList<>();

  private LocalDateTime timestampSendDate;

}
