package com.thor.email.domain.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
  @Builder.Default
  private ObjectId id = new ObjectId();

  private ObjectId typeId;

  

}
