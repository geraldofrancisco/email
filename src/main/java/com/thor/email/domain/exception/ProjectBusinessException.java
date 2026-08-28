package com.thor.email.domain.exception;

import org.springframework.http.HttpStatus;

public class ProjectBusinessException extends ProjectException {

  public ProjectBusinessException(String message) {
    super(message, HttpStatus.UNPROCESSABLE_CONTENT, null);
  }
}
