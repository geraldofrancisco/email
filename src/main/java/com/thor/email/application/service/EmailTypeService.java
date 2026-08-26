package com.thor.email.application.service;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;

public interface EmailTypeService {
  EmailTypeDTO create(EmailTypeDTO dto);
}
