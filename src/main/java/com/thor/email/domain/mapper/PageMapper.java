package com.thor.email.domain.mapper;

import static com.thor.email.domain.constants.ProjectConstants.MONGO_ID_NAME;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ScrollPosition;

public abstract class PageMapper {

  protected static ScrollPosition parseCursor(String lastId) {
    if (StringUtils.isBlank(lastId)) {
      return ScrollPosition.keyset();
    }

    return ScrollPosition.forward(Map.of(MONGO_ID_NAME, lastId));
  }
}
