package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_NAME_FIELD;

import com.thor.email.adapters.out.repository.MongoEmailTypeRepository;
import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.mapper.EmailTypeMapper;
import com.thor.email.domain.repository.EmailTypeRepository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
public class EmailTypeRepositoryImpl extends RepositoryBaseImpl<EmailTypeDocument> implements
    EmailTypeRepository {

  private final MongoEmailTypeRepository repository;

  public EmailTypeRepositoryImpl(MongoTemplate mongoTemplate, MongoEmailTypeRepository repository) {
    this.repository = repository;
    super(mongoTemplate);
  }

  @Override
  public EmailTypeDTO save(EmailTypeDTO dto) {
    var document = EmailTypeMapper.toDocument(dto);
    repository.save(document);
    return dto;
  }

  @Override
  public Window<EmailTypeDTO> getByFilter(EmailTypeFilterDTO filter) {
    var criteriaList = new ArrayList<Criteria>();

    if (StringUtils.isNotBlank(filter.getName())) {
      criteriaList.add(this.like(EMAIL_TYPE_NAME_FIELD, filter.getName()));
    }

    return this.getByCriteria(filter, criteriaList)
        .map(EmailTypeMapper::toDTO);
  }

  @Override
  public Optional<EmailTypeDTO> getById(ObjectId id) {
    return repository.findById(id).map(EmailTypeMapper::toDTO);
  }
}
