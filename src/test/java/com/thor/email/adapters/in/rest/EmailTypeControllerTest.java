package com.thor.email.adapters.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.application.service.EmailTypeService;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
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
class EmailTypeControllerTest {

  @Mock
  private EmailTypeService service;
  @InjectMocks
  private EmailTypeController controller;

  @Test
  void createsTypeAndReturnsCreatedTypeDetails() {
    doAnswer(invocation -> invocation.getArgument(0)).when(service).create(any(EmailTypeDTO.class));

    var response = controller.create(
        EmailTypeRequest.builder().name("welcome").body("<p>hello</p>").build());

    var captor = ArgumentCaptor.forClass(EmailTypeDTO.class);
    verify(service).create(captor.capture());
    assertEquals("welcome", captor.getValue().getName());
    assertEquals("<p>hello</p>", captor.getValue().getBody());
    assertEquals(captor.getValue().getId().toString(), response.getId());
    assertEquals("welcome", response.getName());
  }

  @Test
  void mapsTypeFilterAndPage() {
    var type = EmailTypeDTO.builder().id(new ObjectId()).timestampCreatedDate(
        LocalDateTime.of(2024, 2, 3, 4, 5)).name("welcome").body("<p>hello</p>").build();
    when(service.getByFilter(any(EmailTypeFilterDTO.class))).thenReturn(
        EmailTypePageDTO.builder().content(List.of(type)).hasNext(false).build());

    var response = controller.getByFilter("welcome", 9, type.getId().toString());

    var captor = ArgumentCaptor.forClass(EmailTypeFilterDTO.class);
    verify(service).getByFilter(captor.capture());
    assertEquals("welcome", captor.getValue().getName());
    assertEquals(9, captor.getValue().getSize());
    assertFalse(captor.getValue().getScrollPosition().isInitial());
    assertEquals(type.getId().toString(), response.getContent().get(0).getId());
    assertFalse(response.getHasNext());
  }

  @Test
  void returnsTypeByIdAndPropagatesLookupFailure() {
    var id = new ObjectId();
    var type = EmailTypeDTO.builder().id(id).name("welcome").body("<p>hello</p>").build();
    when(service.getById(id.toString())).thenReturn(type);

    var response = controller.getById(id.toString());
    assertEquals(id.toString(), response.getId());
    assertEquals("welcome", response.getName());

    var failure = new IllegalArgumentException("invalid id");
    when(service.getById("bad")).thenThrow(failure);
    var thrown = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
        () -> controller.getById("bad"));
    assertSame(failure, thrown);
  }
}
