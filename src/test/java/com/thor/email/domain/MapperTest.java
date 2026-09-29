package com.thor.email.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.mapper.PageMapper;
import com.thor.email.domain.mapper.EmailTypeMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;

class MapperTest {

  private static final ObjectId EMAIL_ID = new ObjectId("507f1f77bcf86cd799439011");
  private static final ObjectId TYPE_ID = new ObjectId("507f1f77bcf86cd799439012");
  private static final LocalDateTime CREATED = LocalDateTime.of(2024, 1, 2, 3, 4, 5);
  private static final LocalDateTime SENT = LocalDateTime.of(2024, 1, 3, 4, 5, 6);

  @Test
  void mapsEmailDtoAndDocumentInBothDirections() {
    var dto = EmailDTO.builder()
        .id(EMAIL_ID)
        .typeId(TYPE_ID)
        .timestampCreatedDate(CREATED)
        .title("Welcome")
        .body("<p>Hello</p>")
        .to(List.of("to@example.com"))
        .bcc(List.of("bcc@example.com"))
        .timestampSendDate(SENT)
        .build();

    EmailDocument document = EmailMapper.toDocument(dto);
    assertEquals(EMAIL_ID, document.getId());
    assertEquals(TYPE_ID, document.getEmailTypeId());
    assertEquals(CREATED, document.getTimestampCreatedDate());
    assertEquals("Welcome", document.getTitle());
    assertEquals("<p>Hello</p>", document.getBody());
    assertEquals(List.of("to@example.com"), document.getTo());
    assertEquals(List.of("bcc@example.com"), document.getBcc());
    assertEquals(SENT, document.getTimestampSendDate());

    EmailDTO mapped = EmailMapper.toDTO(document);
    assertEquals(dto, mapped);
  }

  @Test
  void mapsEmailFilterWithOptionalAndInvalidValues() {
    EmailFilterDTO full = EmailMapper.toFilter(
        "2024-01-02T03:04:05.000Z", "2024-01-02T04:05:06.000Z",
        TYPE_ID.toString(), "2024-01-03T04:05:06.000Z",
        "2024-01-03T05:06:07.000Z", EMAIL_ID.toString(), 25, "ASC");

    assertEquals(LocalDateTime.of(2024, 1, 2, 3, 4, 5), full.getStartCreatedDate());
    assertEquals(LocalDateTime.of(2024, 1, 2, 4, 5, 6), full.getEndCreatedDate());
    assertEquals(TYPE_ID, full.getEmailTypeId());
    assertEquals(LocalDateTime.of(2024, 1, 3, 4, 5, 6), full.getStartSendDate());
    assertEquals(LocalDateTime.of(2024, 1, 3, 5, 6, 7), full.getEndSendDate());
    assertEquals(25, full.getSize());
    assertEquals(org.springframework.data.domain.Sort.Direction.ASC, full.getDirection());
    assertFalse(full.getScrollPosition().isInitial());

    EmailFilterDTO sparse = EmailMapper.toFilter(
        "bad", "bad", "invalid", "bad", "bad", null, 0, "DESC");
    assertNull(sparse.getStartCreatedDate());
    assertNull(sparse.getEndCreatedDate());
    assertNull(sparse.getEmailTypeId());
    assertNull(sparse.getStartSendDate());
    assertNull(sparse.getEndSendDate());
    assertTrue(sparse.getScrollPosition().isInitial());
    assertEquals(0, sparse.getSize());
    assertThrows(IllegalArgumentException.class,
        () -> EmailMapper.toFilter(null, null, null, null, null, null, 1, "sideways"));
  }

  @Test
  void mapsEmailWindowsWithAndWithoutNextPosition() {
    EmailDTO first = EmailDTO.builder().id(EMAIL_ID).build();
    EmailDTO last = EmailDTO.builder().id(TYPE_ID).build();
    EmailPageDTO withNext = EmailMapper.toPageDTO(Window.from(
        List.of(first, last), index -> ScrollPosition.forward(Map.of("_id", index)), true));
    assertEquals(List.of(first, last), withNext.getContent());
    assertTrue(withNext.getHasNext());
    assertEquals(TYPE_ID.toString(), withNext.getNextPosition());

    EmailPageDTO terminal = EmailMapper.toPageDTO(
        Window.from(List.of(first), index -> ScrollPosition.forward(Map.of("_id", index)),
            false));
    assertFalse(terminal.getHasNext());
    assertNull(terminal.getNextPosition());

    EmailPageDTO emptyButHasNext = EmailMapper.toPageDTO(
        Window.from(List.of(), item -> ScrollPosition.keyset(), true));
    assertTrue(emptyButHasNext.getHasNext());
    assertNull(emptyButHasNext.getNextPosition());
  }

  @Test
  void mapsEmailTypeDtoAndDocumentInBothDirections() {
    var dto = EmailTypeDTO.builder()
        .id(TYPE_ID)
        .timestampCreatedDate(CREATED)
        .name("Welcome")
        .body("<p>Hello</p>")
        .build();

    EmailTypeDocument document = EmailTypeMapper.toDocument(dto);
    assertEquals(TYPE_ID, document.getId());
    assertEquals(CREATED, document.getTimestampCreatedDate());
    assertEquals("Welcome", document.getName());
    assertEquals("<p>Hello</p>", document.getBody());
    assertEquals(dto, EmailTypeMapper.toDTO(document));
  }

  @Test
  void mapsEmailTypeFiltersAndWindows() {
    EmailTypeFilterDTO filter = EmailTypeMapper.toFilter("News", 10, TYPE_ID.toString());
    assertEquals("News", filter.getName());
    assertEquals(10, filter.getSize());
    assertFalse(filter.getScrollPosition().isInitial());
    assertEquals(org.springframework.data.domain.Sort.Direction.DESC, filter.getDirection());

    EmailTypeFilterDTO initial = EmailTypeMapper.toFilter(null, null, " ");
    assertNull(initial.getName());
    assertNull(initial.getSize());
    assertTrue(initial.getScrollPosition().isInitial());

    EmailTypeDTO first = EmailTypeDTO.builder().id(EMAIL_ID).build();
    EmailTypeDTO last = EmailTypeDTO.builder().id(TYPE_ID).build();
    EmailTypePageDTO page = EmailTypeMapper.toPageDTO(Window.from(
        List.of(first, last), index -> ScrollPosition.forward(Map.of("_id", index)), true));
    assertEquals(List.of(first, last), page.getContent());
    assertTrue(page.getHasNext());
    assertEquals(TYPE_ID.toString(), page.getNextPosition());

    EmailTypePageDTO terminal = EmailTypeMapper.toPageDTO(
        Window.from(List.of(first), index -> ScrollPosition.forward(Map.of("_id", index)),
            false));
    assertFalse(terminal.getHasNext());
    assertNull(terminal.getNextPosition());
    assertNotNull(page);
  }

  @Test
  void initializesPageMapperBaseClass() {
    assertNotNull(new PageMapper() {});
  }
}
