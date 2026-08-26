package com.thor.email.domain.dto.page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageDTO<T> {

  private List<T> content;
  private Boolean hasNext;
  private String nextPosition;
}
