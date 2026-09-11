package com.thor.email.domain.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FieldType {
  SIMPLE,
  LIST;

  public static FieldType toField(String typeString) {
    return Arrays.stream(FieldType.values())
        .filter(type -> type.name().equals(typeString))
        .findFirst()
        .orElse(null);
  }
}
