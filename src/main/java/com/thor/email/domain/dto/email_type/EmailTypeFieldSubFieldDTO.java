package com.thor.email.domain.dto.email_type;

import com.thor.email.domain.enums.FieldTypeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldSubFieldDTO {

  private String name;
  private boolean required;
  private FieldTypeFormat valueType;
}
