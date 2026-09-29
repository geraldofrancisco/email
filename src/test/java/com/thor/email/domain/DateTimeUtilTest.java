package com.thor.email.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thor.email.domain.util.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DateTimeUtilTest {

  @Test
  void validatesDatesAndDateTimesIncludingOptionalValues() {
    assertTrue(DateTimeUtil.validateDate(null));
    assertTrue(DateTimeUtil.validateDate("  "));
    assertTrue(DateTimeUtil.validateDate("2024-02-29"));
    assertFalse(DateTimeUtil.validateDate("2023-02-29"));
    assertFalse(DateTimeUtil.validateDate("2024-2-09"));

    assertTrue(DateTimeUtil.validateDateTime(null));
    assertTrue(DateTimeUtil.validateDateTime(""));
    assertTrue(DateTimeUtil.validateDateTime("2024-02-29T12:34:56.789Z"));
    assertFalse(DateTimeUtil.validateDateTime("2024-02-29"));
    assertFalse(DateTimeUtil.validateDateTime("2023-02-29T12:34:56.789Z"));
    assertFalse(DateTimeUtil.validateDateTime("not-a-date"));
  }

  @Test
  void parsesDatesAndDateTimesAndRejectsMalformedValues() {
    assertNull(DateTimeUtil.toLocalDate(null));
    assertNull(DateTimeUtil.toLocalDate(" "));
    assertEquals(LocalDate.of(2024, 2, 29), DateTimeUtil.toLocalDate("2024-02-29"));
    assertThrows(RuntimeException.class, () -> DateTimeUtil.toLocalDate("2024-2-29"));

    assertNull(DateTimeUtil.toLocalDateTime(null));
    assertNull(DateTimeUtil.toLocalDateTime(""));
    assertEquals(LocalDateTime.of(2024, 2, 29, 12, 34, 56, 789_000_000),
        DateTimeUtil.toLocalDateTime("2024-02-29T12:34:56.789Z"));
    assertThrows(RuntimeException.class,
        () -> DateTimeUtil.toLocalDateTime("2024-02-29T12:34:56Z"));
  }
}
