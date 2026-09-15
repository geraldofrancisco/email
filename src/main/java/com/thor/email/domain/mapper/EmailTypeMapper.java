package com.thor.email.domain.mapper;

import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.document.email_type.EmailTypeFieldDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFilterDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Window;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeMapper extends PageMapper {

  public static EmailTypeDocument toDocument(EmailTypeDTO dto) {
    return EmailTypeDocument.builder()
        .id(dto.getId())
        .timestampCreatedDate(dto.getTimestampCreatedDate())
        .body(dto.getBody())
        .name(dto.getName())
        .build();
  }

  public static EmailTypeDTO toDTO(EmailTypeDocument document) {
    return EmailTypeDTO.builder()
        .id(document.getId())
        .timestampCreatedDate(document.getTimestampCreatedDate())
        .name(document.getName())
        .body(document.getBody())
        .build();
  }

  public static EmailTypeFilterDTO toFilter(String name, Integer size, String cursor) {
    return EmailTypeFilterDTO.builder()
        .name(name)
        .size(size)
        .scrollPosition(parseCursor(cursor))
        .build();
  }

  public static EmailTypePageDTO toPageDTO(Window<EmailTypeDTO> window) {
    return EmailTypePageDTO.builder()
        .content(window.getContent())
        .hasNext(window.hasNext())
        .nextPosition(window.hasNext() ? window.getContent().stream()
            .map(EmailTypeDTO::getId)
            .map(ObjectId::toString)
            .reduce((_, second) -> second).orElse(null) : null
        )
        .build();
  }

}
