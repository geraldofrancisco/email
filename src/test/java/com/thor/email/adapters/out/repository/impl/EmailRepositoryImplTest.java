package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATION_DATETIME_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TIMESTAMP_SEND_DATE_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TYPE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thor.email.adapters.out.repository.MongoEmailRepository;
import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
class EmailRepositoryImplTest {

  @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
  private MongoTemplate mongoTemplate;
  @Mock
  private MongoEmailRepository repository;

  @Test
  void savesMappedDocumentAndReturnsOriginalDto() {
    var id = new ObjectId();
    var dto = EmailDTO.builder().id(id).typeId(new ObjectId()).title("Title")
        .body("<p>body</p>").to(List.of("to@example.com")).bcc(List.of()).build();
    var implementation = new EmailRepositoryImpl(mongoTemplate, repository);

    assertEquals(dto, implementation.save(dto));
    var captor = ArgumentCaptor.forClass(EmailDocument.class);
    verify(repository).save(captor.capture());
    assertEquals(id, captor.getValue().getId());
    assertEquals(dto.getTypeId(), captor.getValue().getEmailTypeId());
    assertEquals(dto.getTitle(), captor.getValue().getTitle());
    assertEquals(dto.getBody(), captor.getValue().getBody());
    assertEquals(dto.getTo(), captor.getValue().getTo());
  }

  @Test
  void queriesWithoutOptionalCriteriaAndMapsWindow() {
    var filter = EmailFilterDTO.builder().size(4).scrollPosition(ScrollPosition.keyset()).build();
    var doc = EmailDocument.builder().id(new ObjectId()).title("Title").build();
    var window = Window.from(List.of(doc),
        index -> ScrollPosition.forward(Map.of("_id", index)), false);
    when(mongoTemplate.query(EmailDocument.class).matching(any(Query.class))
        .scroll(any(ScrollPosition.class))).thenReturn(window);

    var implementation = new EmailRepositoryImpl(mongoTemplate, repository);
    var result = implementation.getByFilter(filter);

    assertEquals(1, result.getContent().size());
    assertEquals(doc.getId(), result.getContent().get(0).getId());
    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate.query(EmailDocument.class), org.mockito.Mockito.times(2))
        .matching(queryCaptor.capture());
    var query = queryCaptor.getAllValues().get(1);
    assertEquals(4, query.getLimit());
    assertEquals(-1, query.getSortObject().get("id"));
    assertEquals(0, query.getQueryObject().size());
  }

  @Test
  void addsEveryProvidedEmailFilterCriterion() {
    var typeId = new ObjectId();
    var createdFrom = LocalDateTime.of(2024, 1, 1, 0, 0);
    var createdTo = createdFrom.plusDays(1);
    var sentFrom = createdFrom.plusDays(2);
    var sentTo = sentFrom.plusDays(1);
    var filter = EmailFilterDTO.builder().size(25).emailTypeId(typeId)
        .startCreatedDate(createdFrom).endCreatedDate(createdTo)
        .scrollPosition(ScrollPosition.keyset())
        .startSendDate(sentFrom).endSendDate(sentTo).build();
    when(mongoTemplate.query(EmailDocument.class).matching(any(Query.class))
        .scroll(any(ScrollPosition.class))).thenReturn(Window.from(
            List.of(), index -> ScrollPosition.forward(Map.of("_id", index)), false));

    var implementation = new EmailRepositoryImpl(mongoTemplate, repository);
    implementation.getByFilter(filter);

    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate.query(EmailDocument.class), org.mockito.Mockito.times(2))
        .matching(queryCaptor.capture());
    var criteria = queryCaptor.getAllValues().get(1).getQueryObject();
    var clauses = criteria.getList("$and", Document.class);
    assertTrue(clauses.stream().anyMatch(clause -> typeId.equals(clause.get(EMAIL_TYPE_ID))));
    assertTrue(clauses.stream().anyMatch(clause ->
        createdFrom.equals(clause.getEmbedded(List.of(EMAIL_CREATION_DATETIME_FIELD, "$gte"),
            Object.class))));
    assertTrue(clauses.stream().anyMatch(clause ->
        createdTo.equals(clause.getEmbedded(List.of(EMAIL_CREATION_DATETIME_FIELD, "$lte"),
            Object.class))));
    assertTrue(clauses.stream().anyMatch(clause ->
        sentFrom.equals(clause.getEmbedded(List.of(EMAIL_TIMESTAMP_SEND_DATE_FIELD, "$gte"),
            Object.class))));
    assertTrue(clauses.stream().anyMatch(clause ->
        sentTo.equals(clause.getEmbedded(List.of(EMAIL_TIMESTAMP_SEND_DATE_FIELD, "$lte"),
            Object.class))));
  }
}
