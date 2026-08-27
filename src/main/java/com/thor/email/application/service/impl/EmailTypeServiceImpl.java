package com.thor.email.application.service.impl;

import com.thor.email.application.service.EmailTypeService;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.mapper.EmailTypeMapper;
import com.thor.email.domain.repository.EmailTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTypeServiceImpl implements EmailTypeService {

  private final EmailTypeRepository repository;

  @Override
  public EmailTypeDTO create(EmailTypeDTO dto) {
    return repository.save(dto);
  }

  @Override
  public EmailTypePageDTO getByFilter(EmailTypeFilterDTO filter) {
    var response = repository.getByFilter(filter);
    return EmailTypeMapper.toPageDTO(response);
  }
}
