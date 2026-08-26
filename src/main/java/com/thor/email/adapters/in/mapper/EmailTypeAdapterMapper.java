package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldDTO;
import com.thor.email.domain.request.email_type.EmailTypeFieldRequest;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.response.EmailTypeResponse;
import java.util.List;
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

  private static List<EmailTypeFieldDTO> toFieldDTO(List<EmailTypeFieldRequest> list) {
    return list.parallelStream()
        .map(field -> EmailTypeFieldDTO.builder()
            .name(field.getName())
            .required(field.isRequired())
            .build()
        )
        .toList();
  }

  public static EmailTypeResponse toResponse(EmailTypeDTO dto) {
    return EmailTypeResponse.builder()
        .id(dto.getId().toHexString())
        .name(dto.getName())
        .build();
  }
}
