package com.thor.email.application.service;

import com.thor.email.domain.dto.email.EmailCreateDTO;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;

public interface EmailService {

  EmailDTO create(EmailCreateDTO dto);

  EmailPageDTO getByFilter(EmailFilterDTO filter);
}
