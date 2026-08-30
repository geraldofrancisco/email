package com.thor.email.adapters.in.rest;

import com.thor.email.adapters.in.mapper.EmailAdapterMapper;
import com.thor.email.adapters.in.rest.swagger.EmailSwagger;
import com.thor.email.application.service.EmailService;
import com.thor.email.domain.mapper.EmailMapper;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.response.email.EmailCreateResponse;
import com.thor.email.domain.response.email.EmailPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController implements EmailSwagger {

  private final EmailService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public EmailCreateResponse create(EmailCreateRequest request) {
    var dto = EmailAdapterMapper.toCreate(request);
    var response = service.create(dto);
    return EmailAdapterMapper.toCreateResponse(response);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public EmailPageResponse getByFilter(Integer size, String startCreateDate, String endCreateDate,
      String emailTypeId, String startSendDate, String endSendDate, String cursor) {
    var filter = EmailMapper.toFilter(startCreateDate, endCreateDate, emailTypeId, startSendDate,
        endSendDate, cursor, size);
    var response = service.getByFilter(filter);
    return EmailAdapterMapper.toPageResponse(response);
  }


}
