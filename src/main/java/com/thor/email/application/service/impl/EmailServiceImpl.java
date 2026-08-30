package com.thor.email.application.service.impl;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE__EMAIL_TYPE_NOT_FOUND;

import com.thor.email.application.service.EmailService;
import com.thor.email.domain.dto.email.EmailCreateDTO;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.exception.ProjectNotFoundException;
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
  public EmailDTO create(EmailCreateDTO dto) {
    var type = emailTypeRepository.getById(dto.getTypeId())
        .orElseThrow(() -> new ProjectNotFoundException(EMAIL_CREATE__EMAIL_TYPE_NOT_FOUND));

    dto.setEmailType(type);
    dto.validateFields();
    dto.generateBody();
    return repository.save(dto);
  }

  @Override
  public EmailPageDTO getByFilter(EmailFilterDTO filter) {
    var response = repository.getByFilter(filter);
    return EmailMapper.toPageDTO(response);
  }
}
