package com.thor.email.adapters.in.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.request.email.EmailCreateRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

class EmailAdapterMapperTest {

  @Test
  void mapsCreateRequestAndCreateResponse() {
    var request = EmailCreateRequest.builder().body("<p>hello</p>").build();
    var dto = EmailAdapterMapper.toCreate(request);
    assertEquals(request.getBody(), dto.getBody());

    dto.setId(new ObjectId("507f1f77bcf86cd799439011"));
    var response = EmailAdapterMapper.toCreateResponse(dto);
    assertEquals(dto.getId().toString(), response.getId());
    assertEquals(dto.getBody(), response.getBody());
  }

  @Test
  void mapsPageMetadataAndAllEmailFields() {
    var id = new ObjectId("507f1f77bcf86cd799439011");
    var typeId = new ObjectId("507f1f77bcf86cd799439012");
    var created = LocalDateTime.of(2024, 2, 3, 4, 5);
    var sent = created.plusHours(1);
    var email = EmailDTO.builder()
        .id(id).typeId(typeId).timestampCreatedDate(created).title("Title")
        .body("<p>Body</p>").to(List.of("to@example.com")).bcc(List.of("bcc@example.com"))
        .timestampSendDate(sent).build();

    var response = EmailAdapterMapper.toPageResponse(EmailPageDTO.builder()
        .content(List.of(email)).hasNext(true).nextPosition(id.toString()).build());

    assertTrue(response.getHasNext());
    assertEquals(id.toString(), response.getNextPosition());
    var mapped = response.getContent().get(0);
    assertEquals(id.toString(), mapped.getId());
    assertEquals(typeId.toString(), mapped.getEmailTypeId());
    assertEquals(created, mapped.getTimestampCreatedDate());
    assertEquals("Title", mapped.getTitle());
    assertEquals("<p>Body</p>", mapped.getBody());
    assertEquals(List.of("to@example.com"), mapped.getTo());
    assertEquals(List.of("bcc@example.com"), mapped.getBcc());
    assertEquals(sent, mapped.getTimestampSendDate());

    var empty = EmailAdapterMapper.toPageResponse(EmailPageDTO.builder()
        .content(List.of()).hasNext(false).build());
    assertFalse(empty.getHasNext());
    assertTrue(empty.getContent().isEmpty());
  }
}
