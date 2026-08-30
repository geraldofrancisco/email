package com.thor.email.domain.mapper;

import com.thor.email.domain.document.email.EmailDocument;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFilterDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Window;

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
        .bcc(dto.getBcc())
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
        .bcc(document.getBcc())
        .timestampSendDate(document.getTimestampSendDate())
        .build();
  }

  public static EmailFilterDTO toFilter(
      String startCreateDate, String endCreateDate, String emailTypeId, String startSendDate,
      String endSendDate, String cursor, Integer size
  ) {
    var builder = EmailFilterDTO.builder()
        .scrollPosition(parseCursor(cursor))
        .size(size);

    if (StringUtils.isNotBlank(emailTypeId) && ObjectId.isValid(emailTypeId)) {
      builder.emailTypeId(new ObjectId(emailTypeId));
    }

    if (DateTimeUtil.validateDateTime(startCreateDate)) {
      builder.startCreateDate(DateTimeUtil.toLocalDateTime(startCreateDate));
    }

    if (DateTimeUtil.validateDateTime(endCreateDate)) {
      builder.endCreateDate(DateTimeUtil.toLocalDateTime(endCreateDate));
    }

    if (DateTimeUtil.validateDateTime(startSendDate)) {
      builder.startSendDate(DateTimeUtil.toLocalDateTime(startSendDate));
    }

    if (DateTimeUtil.validateDateTime(endSendDate)) {
      builder.endSendDate(DateTimeUtil.toLocalDateTime(endSendDate));
    }

    return builder.build();
  }

  public static EmailPageDTO toPageDTO(Window<EmailDTO> window) {
    return EmailPageDTO.builder()
        .content(window.getContent())
        .hasNext(window.hasNext())
        .nextPosition(window.hasNext() ? window.getContent().stream()
            .map(EmailDTO::getId)
            .map(ObjectId::toString)
            .reduce((_, second) -> second).orElse(null) : null
        )
        .build();
  }
}
