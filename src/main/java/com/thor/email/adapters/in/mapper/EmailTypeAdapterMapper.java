package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldDTO;
import com.thor.email.domain.dto.email_type.EmailTypePageDTO;
import com.thor.email.domain.request.email_type.EmailTypeFieldRequest;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.response.email_type.EmailTypeFieldResponse;
import com.thor.email.domain.response.email_type.EmailTypePageResponse;
import com.thor.email.domain.response.email_type.EmailTypeCreateResponse;
import com.thor.email.domain.response.email_type.EmailTypeResponse;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeAdapterMapper {

  public static EmailTypeDTO toCreate(EmailTypeRequest request) {
    return EmailTypeDTO.builder()
        .name(request.getName())
        .body(request.getBody())
        .fields(toFieldDTO(request.getFields()))
        .build();
  }

  private static List<EmailTypeFieldDTO> toFieldDTO(Set<EmailTypeFieldRequest> list) {
    return list.parallelStream()
        .map(field -> EmailTypeFieldDTO.builder()
            .name(field.getName())
            .required(field.isRequired())
            .build()
        )
        .toList();
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
        .fields(toListFieldResponse(dto.getFields()))
        .build();
  }

  private static List<EmailTypeFieldResponse> toListFieldResponse(List<EmailTypeFieldDTO> list) {
    return list.stream()
        .map(f -> EmailTypeFieldResponse.builder()
            .name(f.getName())
            .required(f.isRequired())
            .build()
        ).toList();
  }
}
