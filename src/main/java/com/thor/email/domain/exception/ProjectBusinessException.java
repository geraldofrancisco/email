package com.thor.email.domain.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ProjectBusinessException extends ProjectException {

  public ProjectBusinessException(String message) {
    super(message, HttpStatus.UNPROCESSABLE_CONTENT, null, null);
  }

  public ProjectBusinessException(List<String> messages) {
    super(null, HttpStatus.UNPROCESSABLE_CONTENT, null, messages);
  }
}
