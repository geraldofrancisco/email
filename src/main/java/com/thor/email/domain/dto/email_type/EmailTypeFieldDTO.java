package com.thor.email.domain.dto.email_type;

import com.thor.email.domain.enums.FieldType;
import com.thor.email.domain.enums.FieldTypeFormat;
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

  private boolean required ;

  private FieldType type;

  private FieldTypeFormat valueType;

  @Default
  private List<EmailTypeFieldSubFieldDTO> subFields = new ArrayList<>();

}
