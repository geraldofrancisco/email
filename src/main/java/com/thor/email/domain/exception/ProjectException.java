package com.thor.email.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class ProjectException extends RuntimeException {

  private final String message;
  private final HttpStatus status;
  private final Exception e;
}
