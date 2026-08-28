package com.thor.email.domain.mapper;

import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailMapper extends PageMapper {

  public static EmailDocument toDocument(EmailDTO dto) {
    return EmailDocument.builder()
        .id(dto.getId())
        .emailTypeId(dto.getTypeId())
        .timestampCreatedDate(dto.getTimestampCreatedDate())
        .title(dto.getTitle())
        .body(dto.getBody())
        .to(dto.getTo())
        .cco(dto.getCco())
        .timestampSendDate(dto.getTimestampSendDate())
        .build();
  }

  public static EmailDTO toDTO(EmailDocument document) {
    return EmailDTO.builder()
        .id(document.getId())
        .typeId(document.getEmailTypeId())
        .timestampCreatedDate(document.getTimestampCreatedDate())
        .title(document.getTitle())
        .body(document.getBody())
        .to(document.getTo())
        .cco(document.getCco())
        .timestampSendDate(document.getTimestampSendDate())
        .build();
  }
}
