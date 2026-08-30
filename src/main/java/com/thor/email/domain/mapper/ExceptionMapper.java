package com.thor.email.domain.mapper;

import com.thor.email.domain.response.exception.ExceptionFieldResponse;
import com.thor.email.domain.response.exception.ExceptionResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMapper {

  public static ResponseEntity<ExceptionResponse> toResponse(final HttpStatus status,
      final String message) {
    return toResponse(status, message, null);
  }

  public static ResponseEntity<ExceptionResponse> toResponse(final HttpStatus status,
      final List<ExceptionFieldResponse> fields) {
    return toResponse(status, null, fields);
  }

  public static ResponseEntity<ExceptionResponse> toResponse(final HttpStatus status,
      final String message, final List<ExceptionFieldResponse> fieldMessages) {

    var response = ExceptionResponse.builder()
        .errorDescription(message)
        .fields(fieldMessages)
        .status(status.value())
        .build();
    return ResponseEntity.status(status).body(response);
  }

}
