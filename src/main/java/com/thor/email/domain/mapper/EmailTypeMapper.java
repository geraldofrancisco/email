package com.thor.email.domain.mapper;

import com.thor.email.domain.document.email_type.EmailTypeDocument;
import com.thor.email.domain.document.email_type.EmailTypeFieldDocument;
import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldDTO;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeMapper {

  public static EmailTypeDocument toDocument(EmailTypeDTO dto) {
    return EmailTypeDocument.builder()
        .id(dto.getId())
        .timestampCreatedDate(dto.getTimestampCreatedDate())
        .body(dto.getBody())
        .name(dto.getName())
        .fields(toFieldsDocument(dto.getFields()))
        .build();
  }

  private static List<EmailTypeFieldDocument> toFieldsDocument(List<EmailTypeFieldDTO> list) {
    return list.parallelStream()
        .map(field -> EmailTypeFieldDocument.builder()
            .name(field.getName())
            .required(field.isRequired())
            .build()
        )
        .toList();
  }

  public static EmailTypeDTO toDTO(EmailTypeDocument document) {
    return EmailTypeDTO.builder()
        .id(document.getId())
        .timestampCreatedDate(document.getTimestampCreatedDate())
        .name(document.getName())
        .body(document.getBody())
        .fields(toFieldsDTO(document.getFields()))
        .build();
  }

  private static List<EmailTypeFieldDTO> toFieldsDTO(List<EmailTypeFieldDocument> list) {
    return list.parallelStream()
        .map(field -> EmailTypeFieldDTO.builder()
            .name(field.getName())
            .required(field.isRequired())
            .build()
        )
        .toList();
  }
}
