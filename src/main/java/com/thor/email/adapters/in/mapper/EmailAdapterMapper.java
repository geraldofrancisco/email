package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.response.email.EmailCreateResponse;
import com.thor.email.domain.response.email.EmailPageResponse;
import com.thor.email.domain.response.email.EmailResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailAdapterMapper {

  public static EmailDTO toCreate(EmailCreateRequest request) {
    return EmailDTO.builder()
        .body(request.getBody())
        .build();
  }

  public static EmailCreateResponse toCreateResponse(EmailDTO dto) {
    return EmailCreateResponse.builder()
        .id(dto.getId().toString())
        .body(dto.getBody())
        .build();
  }

  public static EmailPageResponse toPageResponse(EmailPageDTO dto) {
    return EmailPageResponse.builder()
        .content(EmailAdapterMapper.toListResponse(dto.getContent()))
        .hasNext(dto.getHasNext())
        .nextPosition(dto.getNextPosition())
        .build();
  }

  private static List<EmailResponse> toListResponse(List<EmailDTO> list) {
    return list.parallelStream()
        .map(EmailAdapterMapper::toResponse)
        .toList();
  }

  private static EmailResponse toResponse(EmailDTO dto) {
    return EmailResponse.builder()
        .id(dto.getId().toString())
        .emailTypeId(dto.getTypeId().toString())
        .timestampCreatedDate(dto.getTimestampCreatedDate())
        .title(dto.getTitle())
        .body(dto.getBody())
        .to(dto.getTo())
        .bcc(dto.getBcc())
        .timestampSendDate(dto.getTimestampSendDate())
        .build();
  }
}
