package com.thor.email.adapters.out.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.thor.email.domain.exception.ProjectBusinessException;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class ProjectExceptionHandlerTest {

  @Mock
  private MessageSource messageSource;
  @InjectMocks
  private ProjectExceptionHandler handler;

  @Test
  void mapsGenericExceptionToInternalServerErrorAndFallsBackWhenMessageIsMissing() {
    when(messageSource.getMessage("PROJECT_GENERIC_EXCEPTION", null, Locale.getDefault()))
        .thenThrow(new NoSuchMessageException("PROJECT_GENERIC_EXCEPTION"));

    var response = handler.handlerException(new IllegalStateException("broken"));

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("PROJECT_GENERIC_EXCEPTION", response.getBody().getErrorDescription());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    assertNull(response.getBody().getFields());
  }

  @Test
  void mapsProjectExceptionUsingLocalizedMessageAndItsStatus() {
    when(messageSource.getMessage("domain.error", null, Locale.getDefault()))
        .thenReturn("Localized error");

    var response = handler.handlerProjectException(new ProjectBusinessException("domain.error"));

    assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());
    assertEquals("Localized error", response.getBody().getErrorDescription());
  }

  @Test
  void mapsValidationFieldErrorsIncludingFallbackMessage() {
    when(messageSource.getMessage("required", null, Locale.getDefault())).thenReturn("Required");
    when(messageSource.getMessage("unknown.key", null, Locale.getDefault()))
        .thenThrow(new NoSuchMessageException("unknown.key"));
    BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
    bindingResult.addError(new FieldError("request", "name", null, false, null, null, "required"));
    bindingResult.addError(new FieldError("request", "body", null, false, null, null,
        "unknown.key"));
    var exception = org.mockito.Mockito.mock(MethodArgumentNotValidException.class);
    when(exception.getBindingResult()).thenReturn(bindingResult);

    var response = handler.handlerMethodArgumentNotValidException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(2, response.getBody().getFields().size());
    assertTrueContainsField(response.getBody().getFields(), "name", "Required");
    assertTrueContainsField(response.getBody().getFields(), "body", "unknown.key");
    assertNull(response.getBody().getErrorDescription());
  }

  private static void assertTrueContainsField(
      List<com.thor.email.domain.response.exception.ExceptionFieldResponse> fields,
      String name, String message) {
    org.junit.jupiter.api.Assertions.assertTrue(fields.stream()
        .anyMatch(field -> name.equals(field.getName()) && message.equals(field.getMessage())));
  }
}
