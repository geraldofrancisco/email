package com.thor.email.domain.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort.Direction;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class FilterDTO {

  private ScrollPosition scrollPosition;
  private Integer size;

  @Default
  private Direction direction = Direction.DESC;
}
