package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_NAME_FIELD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.adapters.out.repository.MongoEmailTypeRepository;
import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@ExtendWith(MockitoExtension.class)
class EmailTypeRepositoryImplTest {

  @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
  private MongoTemplate mongoTemplate;
  @Mock
  private MongoEmailTypeRepository repository;

  @Test
  void savesMappedDocumentAndReturnsOriginalDto() {
    var id = new ObjectId();
    var dto = EmailTypeDTO.builder().id(id).name("Welcome").body("<p>body</p>").build();
    var implementation = new EmailTypeRepositoryImpl(mongoTemplate, repository);

    assertEquals(dto, implementation.save(dto));
    var captor = ArgumentCaptor.forClass(EmailTypeDocument.class);
    verify(repository).save(captor.capture());
    assertEquals(id, captor.getValue().getId());
    assertEquals(dto.getName(), captor.getValue().getName());
    assertEquals(dto.getBody(), captor.getValue().getBody());
  }

  @Test
  void queriesAndMapsUnfilteredWindowWhenNameIsBlank() {
    var filter = EmailTypeFilterDTO.builder().name(" ").size(3)
        .scrollPosition(ScrollPosition.keyset()).build();
    var doc = EmailTypeDocument.builder().id(new ObjectId()).name("Welcome").build();
    when(mongoTemplate.query(EmailTypeDocument.class).matching(any(Query.class))
        .scroll(any(ScrollPosition.class))).thenReturn(Window.from(
            List.of(doc), index -> ScrollPosition.forward(Map.of("_id", index)), false));

    var implementation = new EmailTypeRepositoryImpl(mongoTemplate, repository);
    var result = implementation.getByFilter(filter);

    assertEquals(doc.getId(), result.getContent().get(0).getId());
    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate.query(EmailTypeDocument.class), org.mockito.Mockito.times(2))
        .matching(queryCaptor.capture());
    var query = queryCaptor.getAllValues().get(1);
    assertTrue(query.getQueryObject().isEmpty());
    assertEquals(3, query.getLimit());
  }

  @Test
  void addsCaseInsensitiveLiteralNameSearch() {
    var filter = EmailTypeFilterDTO.builder().name("Daily.*").size(10)
        .scrollPosition(ScrollPosition.keyset()).build();
    when(mongoTemplate.query(EmailTypeDocument.class).matching(any(Query.class))
        .scroll(any(ScrollPosition.class))).thenReturn(Window.from(
            List.of(), index -> ScrollPosition.forward(Map.of("_id", index)), false));

    new EmailTypeRepositoryImpl(mongoTemplate, repository).getByFilter(filter);

    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate.query(EmailTypeDocument.class), org.mockito.Mockito.times(2))
        .matching(queryCaptor.capture());
    var query = queryCaptor.getAllValues().get(1).getQueryObject();
    var clause = query.getList("$and", Document.class).get(0);
    var regex = clause.get(EMAIL_TYPE_NAME_FIELD, Pattern.class);
    assertEquals("\\QDaily.*\\E", regex.pattern());
    assertTrue((regex.flags() & Pattern.CASE_INSENSITIVE) != 0);
  }

  @Test
  void looksUpByNameAndMapsOptionalResult() {
    var document = EmailTypeDocument.builder().id(new ObjectId()).name("Welcome")
        .body("<p>body</p>").build();
    when(mongoTemplate.findOne(any(Query.class), eq(EmailTypeDocument.class)))
        .thenReturn(document).thenReturn(null);
    var implementation = new EmailTypeRepositoryImpl(mongoTemplate, repository);

    var result = implementation.getByName("Welcome");

    assertEquals(document.getId(), result.orElseThrow().getId());
    assertTrue(implementation.getByName("missing").isEmpty());
    var captor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate, org.mockito.Mockito.times(2)).findOne(captor.capture(),
        eq(EmailTypeDocument.class));
    assertEquals("Welcome", captor.getAllValues().get(0).getQueryObject()
        .get(EMAIL_TYPE_NAME_FIELD));
  }

  @Test
  void looksUpByIdAndMapsOptionalResult() {
    var id = new ObjectId();
    var document = EmailTypeDocument.builder().id(id).name("Welcome").build();
    when(repository.findById(id)).thenReturn(Optional.of(document));
    when(repository.findById(new ObjectId("507f1f77bcf86cd799439011")))
        .thenReturn(Optional.empty());

    var implementation = new EmailTypeRepositoryImpl(mongoTemplate, repository);
    assertEquals(id, implementation.getById(id).orElseThrow().getId());
    assertTrue(implementation.getById(new ObjectId("507f1f77bcf86cd799439011")).isEmpty());
    verify(repository).findById(id);
  }
}
