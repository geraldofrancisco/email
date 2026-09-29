package com.thor.email.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.repository.EmailRepository;
import com.thor.email.domain.repository.EmailTypeRepository;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock
  private EmailRepository repository;
  @Mock
  private EmailTypeRepository emailTypeRepository;
  @InjectMocks
  private EmailServiceImpl service;

  @Test
  void createsByDelegatingToRepository() {
    var dto = EmailDTO.builder().body("<p>Hello</p>").build();
    when(repository.save(dto)).thenReturn(dto);

    assertSame(dto, service.create(dto));
    verify(repository).save(dto);
  }

  @Test
  void mapsRepositoryWindowWhenSearchingByFilter() {
    var filter = EmailFilterDTO.builder().size(5).build();
    var email = EmailDTO.builder().id(new ObjectId()).build();
    var window = Window.from(List.of(email),
        index -> ScrollPosition.forward(Map.of("_id", index)), false);
    when(repository.getByFilter(filter)).thenReturn(window);

    var page = service.getByFilter(filter);

    assertEquals(List.of(email), page.getContent());
    assertEquals(false, page.getHasNext());
    assertEquals(null, page.getNextPosition());
    verify(repository).getByFilter(filter);
  }
}
