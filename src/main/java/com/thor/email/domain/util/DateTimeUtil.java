package com.thor.email.domain.util;

import static com.thor.email.domain.constants.ProjectConstants.DATE_PATTERN;
import static com.thor.email.domain.constants.ProjectConstants.DATE_TIME_PATTERN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {

  public static boolean validateDate(String date) {
    return validateDateTime(date, false);
  }

  public static boolean validateDateTime(String dateTime) {
    return validateDateTime(dateTime, true);
  }

  private static boolean validateDateTime(String dateTime, boolean isDateTime) {
    if (StringUtils.isBlank(dateTime)) {
      return true;
    }

    try {
      LocalDate response;
      var date = dateTime.substring(0, 10);
      if (isDateTime) {
        response = toLocalDateTime(dateTime).toLocalDate();
      } else {
        response = toLocalDate(dateTime);
      }

      return String.valueOf(response).equals(date);

    } catch (Exception e) {
      return false;
    }
  }

  public static LocalDateTime toLocalDateTime(String dateTime) {
    if (StringUtils.isBlank(dateTime)) {
      return null;
    }
    return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
  }

  public static LocalDate toLocalDate(String date) {
    if (StringUtils.isBlank(date)) {
      return null;
    }
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
  }
}
