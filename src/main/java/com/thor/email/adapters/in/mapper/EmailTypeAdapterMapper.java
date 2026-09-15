package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.response.email_type.EmailTypeCreateResponse;
import com.thor.email.domain.response.email_type.EmailTypePageResponse;
import com.thor.email.domain.response.email_type.EmailTypeResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeAdapterMapper {

  public static EmailTypeDTO toCreate(EmailTypeRequest request) {
    return EmailTypeDTO.builder()
        .name(request.getName())
        .body(request.getBody())
        .build();
  }

  public static EmailTypeCreateResponse toCreateResponse(EmailTypeDTO dto) {
    return EmailTypeCreateResponse.builder()
        .id(dto.getId().toString())
        .name(dto.getName())
        .build();
  }

  public static EmailTypePageResponse toPageResponse(EmailTypePageDTO dto) {
    return EmailTypePageResponse.builder()
        .content(toPageContentResponse(dto.getContent()))
        .hasNext(dto.getHasNext())
        .nextPosition(dto.getNextPosition())
        .build();
  }

  private static List<EmailTypeResponse> toPageContentResponse(List<EmailTypeDTO> list) {
    return list.parallelStream()
        .map(EmailTypeAdapterMapper::toResponse)
        .toList();
  }

  public static EmailTypeResponse toResponse(EmailTypeDTO dto) {
    return EmailTypeResponse.builder()
        .id(dto.getId().toString())
        .timestampCreatedDate(dto.getTimestampCreatedDate())
        .body(dto.getBody())
        .name(dto.getName())
        .build();
  }
}
