package com.thor.email.adapters.in.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

class EmailTypeAdapterMapperTest {

  @Test
  void mapsCreateRequestAndResponse() {
    var request = EmailTypeRequest.builder().name("welcome").body("<p>hello</p>").build();
    var dto = EmailTypeAdapterMapper.toCreate(request);
    assertEquals(request.getName(), dto.getName());
    assertEquals(request.getBody(), dto.getBody());

    dto.setId(new ObjectId("507f1f77bcf86cd799439011"));
    var response = EmailTypeAdapterMapper.toCreateResponse(dto);
    assertEquals(dto.getId().toString(), response.getId());
    assertEquals(dto.getName(), response.getName());
  }

  @Test
  void mapsTypeResponseAndPages() {
    var id = new ObjectId("507f1f77bcf86cd799439011");
    var created = LocalDateTime.of(2024, 2, 3, 4, 5);
    var type = EmailTypeDTO.builder()
        .id(id).timestampCreatedDate(created).name("welcome").body("<p>Hello</p>").build();

    var single = EmailTypeAdapterMapper.toResponse(type);
    assertEquals(id.toString(), single.getId());
    assertEquals(created, single.getTimestampCreatedDate());
    assertEquals("welcome", single.getName());
    assertEquals("<p>Hello</p>", single.getBody());

    var page = EmailTypeAdapterMapper.toPageResponse(EmailTypePageDTO.builder()
        .content(List.of(type)).hasNext(true).nextPosition(id.toString()).build());
    assertTrue(page.getHasNext());
    assertEquals(id.toString(), page.getNextPosition());
    assertEquals(single, page.getContent().get(0));

    var empty = EmailTypeAdapterMapper.toPageResponse(EmailTypePageDTO.builder()
        .content(List.of()).hasNext(false).build());
    assertFalse(empty.getHasNext());
    assertTrue(empty.getContent().isEmpty());
  }
}
