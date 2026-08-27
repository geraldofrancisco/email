package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.EmailTypeConstant.EMAIL_TYPE_NAME_FIELD;
import static com.thor.email.domain.constants.ProjectConstants.MONGO_ID_NAME;

import com.thor.email.adapters.out.repository.MongoEmailTypeRepository;
import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.mapper.EmailTypeMapper;
import com.thor.email.domain.repository.EmailTypeRepository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

  @Override
  public Window<EmailTypeDTO> getByFilter(EmailTypeFilterDTO filter) {
    var query = new Query();
    var criteriaList = new ArrayList<Criteria>();

    if (StringUtils.isNotBlank(filter.getName())) {
      String regexPattern = Pattern.quote(filter.getName());
      criteriaList.add(Criteria.where(EMAIL_TYPE_NAME_FIELD).regex(regexPattern, "i"));
    }

    if (!criteriaList.isEmpty()) {
      query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
    }

    query.with(Sort.by(Direction.DESC, MONGO_ID_NAME));
    query.limit(filter.getSize());

    return mongoTemplate.query(EmailTypeDocument.class)
        .matching(query)
        .scroll(filter.getScrollPosition())
        .map(EmailTypeMapper::toDTO);
  }

  @Override
  public Optional<EmailTypeDTO> getById(ObjectId id) {
    return repository.findById(id).map(EmailTypeMapper::toDTO);
  }
}
