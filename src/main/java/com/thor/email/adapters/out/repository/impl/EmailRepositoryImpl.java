package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_TYPE_ID;
import static com.thor.email.domain.constants.ProjectConstants.MONGO_ID_NAME;

import com.thor.email.adapters.out.repository.MongoEmailRepository;
import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.repository.EmailRepository;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

  @Override
  public Window<EmailDTO> getByFilter(EmailFilterDTO filter) {
    var query = new Query();
    var criteriaList = new ArrayList<Criteria>();

    if (Objects.nonNull(filter.getEmailTypeId())) {
      criteriaList.add(Criteria.where(EMAIL_TYPE_ID).is(filter.getEmailTypeId()));
    }

    //TODO: colocar as condições do criteria aqui

    if (!criteriaList.isEmpty()) {
      query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
    }

    query.with(Sort.by(Direction.DESC, MONGO_ID_NAME));
    query.limit(filter.getSize());

    return mongoTemplate.query(EmailDocument.class)
        .matching(query)
        .scroll(filter.getScrollPosition())
        .map(EmailMapper::toDTO);
  }
}
