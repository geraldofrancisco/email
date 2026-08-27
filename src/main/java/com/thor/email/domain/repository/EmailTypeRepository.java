package com.thor.email.domain.repository;

import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import org.springframework.data.domain.Window;

public interface EmailTypeRepository {

  EmailTypeDTO save(EmailTypeDTO dto);

  Window<EmailTypeDocument> getByFilter(EmailTypeFilterDTO filter);
}
