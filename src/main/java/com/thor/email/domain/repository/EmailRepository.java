package com.thor.email.domain.repository;

import com.thor.email.domain.dto.email.EmailDTO;

public interface EmailRepository {

  EmailDTO save(EmailDTO dto);
}
