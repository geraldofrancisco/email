package com.thor.email.adapters.out.repository.impl;

import com.thor.email.adapters.out.repository.MongoEmailRepository;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

  private final MongoEmailRepository repository;
  private final MongoTemplate mongoTemplate;

  @Override
  public EmailDTO save(EmailDTO dto) {
    var document = EmailMapper.toDocument(dto);
    repository.save(document);
    return dto;
  }
}
