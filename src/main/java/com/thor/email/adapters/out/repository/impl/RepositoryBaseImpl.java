package com.thor.email.adapters.out.repository.impl;

import static com.thor.email.domain.constants.ProjectConstants.MONGO_ID_NAME;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.thor.email.domain.dto.page.FilterDTO;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public abstract class RepositoryBaseImpl<DOCUMENT> {

  private final MongoTemplate mongoTemplate;
  private final Class<DOCUMENT> entityClass;

  protected RepositoryBaseImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
    this.entityClass = (Class<DOCUMENT>) GenericTypeResolver.resolveTypeArgument(
        getClass(), RepositoryBaseImpl.class);
  }

  protected Window<DOCUMENT> getByCriteria(FilterDTO filter,
      List<Criteria> criteriaList) {
    var query = new Query();

    if (!criteriaList.isEmpty()) {
      query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
    }
    query.with(Sort.by(filter.getDirection(), MONGO_ID_NAME));
    query.limit(filter.getSize());

    return mongoTemplate.query(this.entityClass)
        .matching(query)
        .scroll(filter.getScrollPosition());
  }

  protected Criteria like(String fieldName, String value) {
    String regexPattern = Pattern.quote(value);
    return Criteria.where(fieldName).regex(regexPattern, "i");
  }

  protected Criteria equals(String fieldName, Object value) {
    return where(fieldName).is(value);
  }

  protected Criteria greaterThanOrEqualTo(String fieldName, Object value) {
    return where(fieldName).gte(value);
  }

  protected Criteria lessThanOrEqualTo(String fieldName, Object value) {
    return where(fieldName).lte(value);
  }
}
