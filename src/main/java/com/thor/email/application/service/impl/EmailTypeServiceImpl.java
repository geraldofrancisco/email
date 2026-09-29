package com.thor.email.application.service.impl;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_GET_BY_ID_INVALID;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_NOT_FOUND;

import com.thor.email.application.service.EmailTypeService;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.exception.ProjectBusinessException;
import com.thor.email.domain.exception.ProjectNotFoundException;
import com.thor.email.domain.mapper.EmailTypeMapper;
import com.thor.email.domain.repository.EmailTypeRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTypeServiceImpl implements EmailTypeService {

  private final EmailTypeRepository repository;

  @Override
  public EmailTypeDTO create(EmailTypeDTO dto) {
    return repository.getByName(dto.getName())
        .map(type -> {
          type.setBody(dto.getBody());
          return repository.save(type);
        })
        .orElseGet(() -> repository.save(dto));
  }

  @Override
  public EmailTypePageDTO getByFilter(EmailTypeFilterDTO filter) {
    var response = repository.getByFilter(filter);
    return EmailTypeMapper.toPageDTO(response);
  }

  @Override
  public EmailTypeDTO getByName(String name) {
    return repository.getByName(name)
        .orElseThrow(() -> new ProjectNotFoundException(EMAIL_TYPE_NOT_FOUND));
  }

  @Override
  public EmailTypeDTO getById(String id) {
    if (!ObjectId.isValid(id)) {
      throw new ProjectBusinessException(EMAIL_TYPE_GET_BY_ID_INVALID);
    }

    return repository.getById(new ObjectId(id))
        .orElseThrow(() -> new ProjectNotFoundException(EMAIL_TYPE_NOT_FOUND));
  }

}
