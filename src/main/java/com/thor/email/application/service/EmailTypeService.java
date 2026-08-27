package com.thor.email.application.service;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;

public interface EmailTypeService {

  EmailTypeDTO create(EmailTypeDTO dto);

  EmailTypePageDTO getByFilter(EmailTypeFilterDTO filter);

}
