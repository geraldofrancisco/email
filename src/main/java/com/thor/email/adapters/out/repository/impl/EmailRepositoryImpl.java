package com.thor.email.adapters.out.repository.impl;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

  @Override
  public EmailDTO save(EmailDTO dto) {
    return dto;
  }
}
