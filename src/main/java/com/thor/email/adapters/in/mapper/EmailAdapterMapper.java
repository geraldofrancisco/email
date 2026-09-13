package com.thor.email.adapters.in.mapper;

import com.thor.email.domain.dto.email.EmailCreateDTO;
import com.thor.email.domain.dto.email.EmailDTO;
import com.thor.email.domain.dto.email.EmailFieldDTO;
import com.thor.email.domain.dto.email.EmailPageDTO;
import com.thor.email.domain.dto.email.EmailSubfieldDTO;
import com.thor.email.domain.request.email.EmailCreateFieldsValueRequest;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.request.email.EmailCreateSubfieldRequest;
import com.thor.email.domain.response.email.EmailCreateResponse;
import com.thor.email.domain.response.email.EmailPageResponse;
import com.thor.email.domain.response.email.EmailResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailAdapterMapper {

  public static EmailCreateDTO toCreate(EmailCreateRequest request) {
    return EmailCreateDTO.builder()
        .typeId(new ObjectId(request.getEmailTypeId()))
        .filledFields(toListFieldDTO(request.getFieldValues()))
        .build();
  }

  private static List<EmailFieldDTO> toListFieldDTO(Set<EmailCreateFieldsValueRequest> list) {
    if (Objects.isNull(list)) {
      return new ArrayList<>();
    }
    return list.parallelStream()
        .map(EmailAdapterMapper::toFieldDTO)
        .collect(Collectors.toList());
  }

  private static EmailFieldDTO toFieldDTO(EmailCreateFieldsValueRequest request) {
    return EmailFieldDTO.builder()
        .field(request.getName())
        .value(request.getValue())
        .subfields(toListSubFieldDTO(request.getSubfields()))
        .build();
  }

  private static List<EmailSubfieldDTO> toListSubFieldDTO(Set<EmailCreateSubfieldRequest> list) {
    if (Objects.isNull(list)) {
      return new ArrayList<>();
    }
    return list.parallelStream()
        .map(EmailAdapterMapper::toSubFieldDTO)
        .collect(Collectors.toList());
  }

  private static EmailSubfieldDTO toSubFieldDTO(EmailCreateSubfieldRequest request) {
    return EmailSubfieldDTO.builder().build();
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
