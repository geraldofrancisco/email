package com.thor.email.domain.repository;

import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Window;

public interface EmailTypeRepository {

  EmailTypeDTO save(EmailTypeDTO dto);

  Window<EmailTypeDTO> getByFilter(EmailTypeFilterDTO filter);

  Optional<EmailTypeDTO> getById(ObjectId id);
}
