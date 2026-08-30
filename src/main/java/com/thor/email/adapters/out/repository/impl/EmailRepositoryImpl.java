package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATION_DATETIME_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TIMESTAMP_SEND_DATE_FIELD;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_TYPE_ID;

import com.thor.email.adapters.out.repository.MongoEmailRepository;
import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.repository.EmailRepository;
import java.util.ArrayList;
import java.util.Objects;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
public final class EmailRepositoryImpl extends RepositoryBaseImpl<EmailDocument> implements
    EmailRepository {

  private final MongoEmailRepository repository;

  public EmailRepositoryImpl(MongoTemplate mongoTemplate, MongoEmailRepository repository) {
    this.repository = repository;
    super(mongoTemplate);
  }

  @Override
  public EmailDTO save(EmailDTO dto) {
    var document = EmailMapper.toDocument(dto);
    repository.save(document);
    return dto;
  }

  @Override
  public Window<EmailDTO> getByFilter(EmailFilterDTO filter) {
    var list = new ArrayList<Criteria>();

    if (Objects.nonNull(filter.getEmailTypeId())) {
      list.add(this.equals(EMAIL_TYPE_ID, filter.getEmailTypeId()));
    }

    if (Objects.nonNull(filter.getStartCreatedDate())) {
      list.add(
          this.greaterThanOrEqualTo(EMAIL_CREATION_DATETIME_FIELD, filter.getStartCreatedDate()));
    }

    if (Objects.nonNull(filter.getEndCreatedDate())) {
      list.add(this.lessThanOrEqualTo(EMAIL_CREATION_DATETIME_FIELD, filter.getEndCreatedDate()));
    }

    if (Objects.nonNull(filter.getStartSendDate())) {
      list.add(
          this.greaterThanOrEqualTo(EMAIL_TIMESTAMP_SEND_DATE_FIELD, filter.getStartSendDate()));
    }

    if (Objects.nonNull(filter.getEndSendDate())) {
      list.add(this.lessThanOrEqualTo(EMAIL_TIMESTAMP_SEND_DATE_FIELD, filter.getEndSendDate()));
    }

    return this.getByCriteria(filter, list)
        .map(EmailMapper::toDTO);
  }
}
