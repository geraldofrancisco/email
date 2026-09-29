package com.thor.email.adapters.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.application.service.EmailService;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.request.email.EmailCreateRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

  @Mock
  private EmailService service;
  @InjectMocks
  private EmailController controller;

  @Test
  void createsEmailFromRequestAndMapsServiceResult() {
    var saved = EmailDTO.builder()
        .id(new ObjectId("507f1f77bcf86cd799439011")).body("<p>hello</p>").build();
    when(service.create(org.mockito.ArgumentMatchers.any(EmailDTO.class))).thenReturn(saved);

    var response = controller.create(EmailCreateRequest.builder().body("<p>hello</p>").build());

    var captor = ArgumentCaptor.forClass(EmailDTO.class);
    verify(service).create(captor.capture());
    assertEquals("<p>hello</p>", captor.getValue().getBody());
    assertEquals(saved.getId().toString(), response.getId());
    assertEquals(saved.getBody(), response.getBody());
  }

  @Test
  void buildsFilterAndMapsEmailPage() {
    var id = new ObjectId("507f1f77bcf86cd799439011");
    var typeId = new ObjectId("507f1f77bcf86cd799439012");
    var created = LocalDateTime.of(2024, 2, 3, 4, 5);
    var email = EmailDTO.builder().id(id).typeId(typeId).timestampCreatedDate(created)
        .title("Title").body("Body").to(List.of("to@example.com")).bcc(List.of())
        .timestampSendDate(created.plusHours(1)).build();
    when(service.getByFilter(org.mockito.ArgumentMatchers.any(EmailFilterDTO.class)))
        .thenReturn(EmailPageDTO.builder().content(List.of(email)).hasNext(false).build());

    var response = controller.getByFilter(12, "2024-02-03T04:05:00.000Z", null,
        typeId.toString(), null, null, "ASC", null);

    var captor = ArgumentCaptor.forClass(EmailFilterDTO.class);
    verify(service).getByFilter(captor.capture());
    assertEquals(12, captor.getValue().getSize());
    assertEquals(org.springframework.data.domain.Sort.Direction.ASC, captor.getValue().getDirection());
    assertEquals(typeId, captor.getValue().getEmailTypeId());
    assertEquals(created, captor.getValue().getStartCreatedDate());
    assertEquals(id.toString(), response.getContent().get(0).getId());
    assertFalse(response.getHasNext());
  }

  @Test
  void propagatesServiceFailure() {
    var failure = new IllegalStateException("database unavailable");
    when(service.getByFilter(org.mockito.ArgumentMatchers.any(EmailFilterDTO.class)))
        .thenThrow(failure);

    var thrown = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
        () -> controller.getByFilter(1, null, null, null, null, null, "DESC", null));
    assertSame(failure, thrown);
  }
}
