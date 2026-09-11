package com.thor.email.domain.dto.email_type;

import com.thor.email.domain.enums.FieldType;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTypeFieldDTO {

  private String name;

  @Default
  private boolean required = true;

  @Default
  private FieldType type = FieldType.SIMPLE;

  @Default
  private List<EmailTypeFieldSubFieldDTO> subFields = new ArrayList<>();
}
