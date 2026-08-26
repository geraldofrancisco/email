package com.thor.email.domain.repository;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;

public interface EmailTypeRepository {

  EmailTypeDTO save(EmailTypeDTO dto);
}
