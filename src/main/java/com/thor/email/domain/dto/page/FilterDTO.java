package com.thor.email.domain.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.ScrollPosition;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class FilterDTO {

  private ScrollPosition scrollPosition;
  private Integer size;
}
