package com.thor.email.domain.repository;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import org.springframework.data.domain.Window;

public interface EmailRepository {

  EmailDTO save(EmailDTO dto);

  Window<EmailDTO> getByFilter(EmailFilterDTO filter);
}
