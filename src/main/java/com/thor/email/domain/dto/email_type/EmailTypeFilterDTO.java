package com.thor.email.domain.dto.email_type;

import com.thor.email.domain.dto.page.FilterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailTypeFilterDTO extends FilterDTO {

  private String name;
}
