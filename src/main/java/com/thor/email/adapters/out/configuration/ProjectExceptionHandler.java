package com.thor.email.adapters.out.configuration;

import static com.thor.email.domain.constants.ProjectConstants.PROJECT_GENERIC_EXCEPTION;

import com.thor.email.domain.exception.ProjectException;
import com.thor.email.domain.mapper.ExceptionMapper;
import com.thor.email.domain.response.exception.ExceptionFieldResponse;
import com.thor.email.domain.response.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class ProjectExceptionHandler {
  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handlerException(Exception ex) {
    var message = getMessage(PROJECT_GENERIC_EXCEPTION);
    log.error(message, ex);
    return ExceptionMapper.toResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  @ExceptionHandler(ProjectException.class)
  public ResponseEntity<ExceptionResponse> handlerProjectException(ProjectException ex) {
    var message = getMessage(ex.getMessage());
    log.info(message, Objects.requireNonNullElse(ex.getE(), ex));
    return ExceptionMapper.toResponse(ex.getStatus(), message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    var list = ex.getBindingResult().getFieldErrors().parallelStream()
        .map(this::getError)
        .toList();
    return ExceptionMapper.toResponse(HttpStatus.BAD_REQUEST, list);
  }

  private ExceptionFieldResponse getError(FieldError error) {

    return ExceptionFieldResponse.builder()
        .message(getMessage(error.getDefaultMessage()))
        .name(error.getField())
        .build();
  }

  private String getMessage(String error) {
    return messageSource.getMessage(error, null, Locale.getDefault());
  }
}
