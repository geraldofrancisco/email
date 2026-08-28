package com.thor.email.domain.exception;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ProjectException{

  public ProjectNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND, null);
  }
}
