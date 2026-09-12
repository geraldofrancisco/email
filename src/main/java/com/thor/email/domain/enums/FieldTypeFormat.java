package com.thor.email.domain.enums;

import static com.thor.email.domain.constants.ProjectConstants.CURRENCY_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.DATE_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.DATE_TIME_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.NUMBER_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.ONLY_TRUE_REGEX;

import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldTypeFormat {
  DATE(DATE_REGEX),
  DATE_TIME(DATE_TIME_REGEX),
  STRING(ONLY_TRUE_REGEX),
  CURRENCY(CURRENCY_REGEX),
  NUMBER(NUMBER_REGEX);

  private final String regex;

  public static boolean validate(FieldTypeFormat type, String value) {
    if (Objects.isNull(type) || Objects.isNull(value)) {
      return false;
    }
    return value.matches(type.getRegex());
  }

  public static FieldTypeFormat toFormat(String typeString) {
    return Arrays.stream(FieldTypeFormat.values())
        .filter(type -> type.name().equals(typeString))
        .findFirst()
        .orElse(null);
  }
}
