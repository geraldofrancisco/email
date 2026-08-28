package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFieldDTO;
import com.thor.email.domain.request.email.EmailCreateFieldsValueRequest;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.response.email.EmailCreateResponse;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailAdapterMapper {

  public static EmailDTO toCreate(EmailCreateRequest request) {
    return EmailDTO.builder()
        .typeId(new ObjectId(request.getEmailTypeId()))
        .filledFields(toListFieldDTO(request.getFieldValues()))
        .build();
  }

  private static List<EmailFieldDTO> toListFieldDTO(Set<EmailCreateFieldsValueRequest> list) {
    return list.parallelStream()
        .map(EmailAdapterMapper::toFieldDTO)
        .toList();
  }

  private static EmailFieldDTO toFieldDTO(EmailCreateFieldsValueRequest request) {
    return EmailFieldDTO.builder()
        .field(request.getField())
        .value(request.getValue())
        .build();
  }

  public static EmailCreateResponse toCreateResponse(EmailDTO dto) {
    return EmailCreateResponse.builder()
        .id(dto.getId().toString())
        .body(dto.getBody())
        .build();
  }
}
