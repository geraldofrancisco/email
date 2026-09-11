package com.thor.email.domain.enums;

import static com.thor.email.domain.constants.ProjectConstants.CURRENCY_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.DATE_REGEX;
import static com.thor.email.domain.constants.ProjectConstants.DATE_TIME_REGEX;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldTypeFormat {
  DATE(DATE_REGEX),
  DATE_TIME(DATE_TIME_REGEX),
  STRING(EMPTY),
  CURRENCY(CURRENCY_REGEX);

  public final String regex;
}
