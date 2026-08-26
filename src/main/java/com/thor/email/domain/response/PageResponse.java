package com.thor.email.domain.response;

import static com.thor.email.domain.constants.ProjectConstants.PAGE_RESPONSE_CONTENT_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.PAGE_RESPONSE_HAS_NEXT_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.PAGE_RESPONSE_NEXT_POSITION_DESCRIPTION;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageResponse<T> {

  @Schema(description = PAGE_RESPONSE_CONTENT_DESCRIPTION)
  private List<T> content;

  @Schema(description = PAGE_RESPONSE_HAS_NEXT_DESCRIPTION)
  private Boolean hasNext;

  @Schema(description = PAGE_RESPONSE_NEXT_POSITION_DESCRIPTION)
  private String nextPosition;
}
