package com.thor.email.application.service.impl;

import com.thor.email.application.service.EmailService;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.repository.EmailRepository;
import com.thor.email.domain.repository.EmailTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final EmailRepository repository;
  private final EmailTypeRepository emailTypeRepository;

  @Override
  public EmailDTO create(EmailDTO dto) {

    return repository.save(dto);
  }

  @Override
  public EmailPageDTO getByFilter(EmailFilterDTO filter) {
    var response = repository.getByFilter(filter);
    return EmailMapper.toPageDTO(response);
  }
}
