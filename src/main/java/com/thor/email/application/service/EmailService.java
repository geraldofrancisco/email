package com.thor.email.application.service;

import com.thor.email.domain.dto.email.EmailCreateDTO;
import com.thor.email.domain.dto.email.EmailDTO;

public interface EmailService {

  EmailDTO create(EmailCreateDTO dto);
}
