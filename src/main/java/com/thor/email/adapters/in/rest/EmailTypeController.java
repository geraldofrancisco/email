package com.thor.email.adapters.in.rest;

import com.thor.email.adapters.in.mapper.EmailTypeAdapterMapper;
import com.thor.email.adapters.in.rest.swagger.EmailTypeSwagger;
import com.thor.email.application.service.EmailTypeService;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.response.email_type.EmailTypeCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email-type")
@RequiredArgsConstructor
public class EmailTypeController implements EmailTypeSwagger {

  private final EmailTypeService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public EmailTypeCreateResponse create(EmailTypeRequest request) {
    var dto = EmailTypeAdapterMapper.toCreate(request);
    service.create(dto);
    return EmailTypeAdapterMapper.toResponse(dto);
  }
}
