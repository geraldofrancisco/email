package com.thor.email.adapters.out.repository.impl;

import com.thor.email.adapters.out.repository.MongoEmailTypeRepository;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.mapper.EmailTypeMapper;
import com.thor.email.domain.repository.EmailTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailTypeRepositoryImpl implements EmailTypeRepository {

  private final MongoEmailTypeRepository repository;
  private final MongoTemplate mongoTemplate;

  @Override
  public EmailTypeDTO save(EmailTypeDTO dto) {
    var document = EmailTypeMapper.toDocument(dto);
    repository.save(document);
    return dto;
  }
}
