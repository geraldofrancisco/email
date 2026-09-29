package com.thor.email.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.exception.ProjectBusinessException;
import com.thor.email.domain.exception.ProjectNotFoundException;
import com.thor.email.domain.repository.EmailTypeRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;

@ExtendWith(MockitoExtension.class)
class EmailTypeServiceImplTest {

  @Mock
  private EmailTypeRepository repository;
  @InjectMocks
  private EmailTypeServiceImpl service;

  @Test
  void createsNewTypeWhenNameDoesNotExist() {
    var dto = EmailTypeDTO.builder().name("welcome").body("<p>New</p>").build();
    when(repository.getByName("welcome")).thenReturn(Optional.empty());
    when(repository.save(dto)).thenReturn(dto);

    assertSame(dto, service.create(dto));
    verify(repository).save(dto);
  }

  @Test
  void updatesExistingTypeBodyAndSavesExistingType() {
    var existing = EmailTypeDTO.builder().id(new ObjectId()).name("welcome")
        .body("<p>Old</p>").build();
    var request = EmailTypeDTO.builder().name("welcome").body("<p>Updated</p>").build();
    when(repository.getByName("welcome")).thenReturn(Optional.of(existing));
    when(repository.save(existing)).thenReturn(existing);

    assertSame(existing, service.create(request));
    assertEquals("<p>Updated</p>", existing.getBody());
    verify(repository).save(existing);
  }

  @Test
  void mapsRepositoryWindowWhenSearchingByFilter() {
    var filter = EmailTypeFilterDTO.builder().name("welcome").build();
    var type = EmailTypeDTO.builder().id(new ObjectId()).build();
    var window = Window.from(List.of(type),
        index -> ScrollPosition.forward(Map.of("_id", index)), false);
    when(repository.getByFilter(filter)).thenReturn(window);

    var page = service.getByFilter(filter);

    assertEquals(List.of(type), page.getContent());
    assertEquals(false, page.getHasNext());
    assertEquals(null, page.getNextPosition());
    verify(repository).getByFilter(filter);
  }

  @Test
  void returnsTypeByNameOrThrowsNotFound() {
    var type = EmailTypeDTO.builder().name("welcome").build();
    when(repository.getByName("welcome")).thenReturn(Optional.of(type));
    assertSame(type, service.getByName("welcome"));

    when(repository.getByName("missing")).thenReturn(Optional.empty());
    assertThrows(ProjectNotFoundException.class, () -> service.getByName("missing"));
  }

  @Test
  void validatesIdBeforeLookingUpTypeAndThrowsWhenMissing() {
    var validId = new ObjectId();
    var type = EmailTypeDTO.builder().id(validId).build();
    when(repository.getById(validId)).thenReturn(Optional.of(type));

    assertSame(type, service.getById(validId.toString()));
    assertThrows(ProjectBusinessException.class, () -> service.getById("not-an-id"));
    verify(repository, times(1)).getById(validId);

    var absentId = new ObjectId();
    when(repository.getById(absentId)).thenReturn(Optional.empty());
    assertThrows(ProjectNotFoundException.class, () -> service.getById(absentId.toString()));
    verify(repository, times(2)).getById(org.mockito.ArgumentMatchers.any(ObjectId.class));
  }
}
