package com.thor.email.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thor.email.domain.exception.ProjectBusinessException;
import com.thor.email.domain.exception.ProjectException;
import com.thor.email.domain.exception.ProjectNotFoundException;
import com.thor.email.domain.mapper.ExceptionMapper;
import com.thor.email.domain.request.email.EmailCreateFieldsValueRequest;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.request.email_type.EmailTypeFieldRequest;
import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.request.validation.ValidDateTimeFormat;
import com.thor.email.domain.request.validation.ValueOfEnum;
import com.thor.email.domain.request.validation.impl.DateTimeFormatValidator;
import com.thor.email.domain.request.validation.impl.HTMLContentValidator;
import com.thor.email.domain.request.validation.impl.ValidObjectIdValidator;
import com.thor.email.domain.request.validation.impl.ValueOfEnumValidator;
import com.thor.email.domain.response.exception.ExceptionFieldResponse;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.xml.sax.SAXException;

class ValidationAndExceptionTest {

  private static final String SAX_FACTORY_PROPERTY = "javax.xml.parsers.SAXParserFactory";

  private enum SampleEnum {
    FIRST,
    SECOND
  }

  private static class DateFixtures {
    @ValidDateTimeFormat
    String defaultDateTime;

    @ValidDateTimeFormat(dateTime = false)
    String date;

    @ValidDateTimeFormat(dateTime = true, pattern = "uuuu-MM-dd HH:mm")
    String customDateTime;
  }

  private static class EnumFixture {
    @ValueOfEnum(enumClass = SampleEnum.class, excluded = "SECOND")
    String value;
  }

  @Test
  void validatesDateTimeAnnotationsWithDefaultDateDateOnlyAndCustomPatterns() throws Exception {
    var validator = new DateTimeFormatValidator();

    validator.initialize(DateFixtures.class.getDeclaredField("defaultDateTime")
        .getAnnotation(ValidDateTimeFormat.class));
    assertTrue(validator.isValid(null, null));
    assertTrue(validator.isValid("2024-01-02T03:04:05.123Z", null));
    assertFalse(validator.isValid("2024-02-30T03:04:05.123Z", null));
    assertFalse(validator.isValid("2024-01-02", null));

    validator.initialize(DateFixtures.class.getDeclaredField("date")
        .getAnnotation(ValidDateTimeFormat.class));
    assertTrue(validator.isValid("2024-02-29", null));
    assertFalse(validator.isValid("2023-02-29", null));
    assertFalse(validator.isValid("2024-2-09", null));

    validator.initialize(DateFixtures.class.getDeclaredField("customDateTime")
        .getAnnotation(ValidDateTimeFormat.class));
    assertTrue(validator.isValid("2024-01-02 03:04", null));
    assertFalse(validator.isValid("2024-02-30 03:04", null));
  }

  @Test
  void validatesObjectIdsAllowingOnlyBlankOrValidIds() {
    var validator = new ValidObjectIdValidator();
    assertTrue(validator.isValid(null, null));
    assertTrue(validator.isValid("  ", null));
    assertTrue(validator.isValid(new ObjectId().toString(), null));
    assertFalse(validator.isValid("not-an-object-id", null));
  }

  @Test
  void validatesEnumValuesAndExclusions() throws Exception {
    var validator = new ValueOfEnumValidator();
    Annotation constraint = EnumFixture.class.getDeclaredField("value")
        .getAnnotation(ValueOfEnum.class);
    validator.initialize((ValueOfEnum) constraint);
    assertTrue(validator.isValid(null, null));
    assertTrue(validator.isValid("FIRST", null));
    assertFalse(validator.isValid("SECOND", null));
    assertFalse(validator.isValid("first", null));
  }

  @Test
  void validatesHtmlNestingAndCssAndReportsHelpfulErrors() {
    var validator = new HTMLContentValidator();
    var capturedMessage = new AtomicReference<String>();
    ConstraintValidatorContext context = contextCapturing(capturedMessage);

    assertTrue(validator.isValid(null, null));
    assertTrue(validator.isValid("  ", null));
    assertTrue(validator.isValid(
        "<div><p>Hello<br/><img src='x'/></p><style>p { color: red; }</style>"
            + "<span style='color: blue;'></span></div>", context));
    assertTrue(validator.isValid(
        "<div><area/><base/><br/><col/><embed/><hr/><img/><input/><link/><meta/><param/>"
            + "<source/><track/><wbr/></div>", context));
    assertTrue(validator.isValid("<div><style></style><p style=' '></p></div>", context));

    assertFalse(validator.isValid("<div></span>", context));
    assertTrue(capturedMessage.get().contains("Erro de sintaxe no HTML"));
    assertFalse(validator.isValid("<div>", context));
    assertTrue(capturedMessage.get().contains("Erro de sintaxe no HTML"));
    assertFalse(validator.isValid("<div></div></p>", context));
    assertTrue(capturedMessage.get().contains("Erro de sintaxe no HTML"));

    assertFalse(validator.isValid("<style>{</style>", context));
    assertTrue(capturedMessage.get().contains("<style>"));
    assertFalse(validator.isValid("<div style='color:'></div>", context));
    assertTrue(capturedMessage.get().contains("atributo style"));
  }

  @Test
  void exercisesHtmlBalanceHandlerStackAndVoidTagBranches() throws Exception {
    Class<?> handlerClass = Class.forName(
        "com.thor.email.domain.request.validation.impl.HTMLContentValidator$HtmlTagBalanceHandler");
    var constructor = handlerClass.getDeclaredConstructor();
    constructor.setAccessible(true);
    Object handler = constructor.newInstance();
    var start = handlerClass.getMethod("startElement",
        String.class, String.class, String.class, org.xml.sax.Attributes.class);
    var end = handlerClass.getMethod("endElement",
        String.class, String.class, String.class);
    var unclosed = handlerClass.getMethod("getUnclosedTagError");
    start.setAccessible(true);
    end.setAccessible(true);
    unclosed.setAccessible(true);

    start.invoke(handler, "", "", "div", null);
    assertEquals(Optional.of("Erro no HTML: A tag <div> não foi fechada corretamente."),
        unclosed.invoke(handler));
    end.invoke(handler, "", "", "div");
    assertEquals(Optional.empty(), unclosed.invoke(handler));
    end.invoke(handler, "", "", "br");

    assertSaxCause(() -> end.invoke(handler, "", "", "p"));
    start.invoke(handler, "", "", "div", null);
    assertSaxCause(() -> end.invoke(handler, "", "", "span"));
  }

  @Test
  void handlesCustomSaxHandlerExceptionsAndParserConfigurationFailures() {
    String originalFactory = System.getProperty(SAX_FACTORY_PROPERTY);
    var capturedMessage = new AtomicReference<String>();
    var context = contextCapturing(capturedMessage);
    var validator = new HTMLContentValidator();
    try {
      System.setProperty(SAX_FACTORY_PROPERTY, SaxParserFactoryFixture.class.getName());
      SaxParserFactoryFixture.throwOnFeature = false;
      assertFalse(validator.isValid("<div/>", context));
      assertTrue(capturedMessage.get().contains("foi fechada sem ter sido aberta"));

      SaxParserFactoryFixture.throwOnFeature = true;
      assertFalse(validator.isValid("<div/>", context));
      assertTrue(capturedMessage.get().startsWith("Erro ao processar a estrutura do HTML:"));
    } finally {
      SaxParserFactoryFixture.throwOnFeature = false;
      if (originalFactory == null) {
        System.clearProperty(SAX_FACTORY_PROPERTY);
      } else {
        System.setProperty(SAX_FACTORY_PROPERTY, originalFactory);
      }
    }
  }

  @Test
  void mapsExceptionResponsesForMessageAndFieldErrors() {
    var messageResponse = ExceptionMapper.toResponse(HttpStatus.BAD_REQUEST, "invalid");
    assertEquals(HttpStatus.BAD_REQUEST, messageResponse.getStatusCode());
    assertEquals("invalid", messageResponse.getBody().getErrorDescription());
    assertEquals(HttpStatus.BAD_REQUEST.value(), messageResponse.getBody().getStatus());
    assertNull(messageResponse.getBody().getFields());

    var fields = List.of(ExceptionFieldResponse.builder().name("name").message("required").build());
    var fieldResponse = ExceptionMapper.toResponse(HttpStatus.UNPROCESSABLE_CONTENT, fields);
    assertEquals(fields, fieldResponse.getBody().getFields());
    assertNull(fieldResponse.getBody().getErrorDescription());

    var combined = ExceptionMapper.toResponse(HttpStatus.CONFLICT, "conflict", fields);
    assertEquals(HttpStatus.CONFLICT.value(), combined.getBody().getStatus());
    assertEquals("conflict", combined.getBody().getErrorDescription());
    assertEquals(fields, combined.getBody().getFields());
  }

  @Test
  void exposesBusinessAndNotFoundExceptionDetails() {
    ProjectException business = new ProjectBusinessException("invalid operation");
    assertEquals("invalid operation", business.getMessage());
    assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, business.getStatus());
    assertNull(business.getE());
    assertThrows(ProjectBusinessException.class, () -> {
      throw business;
    });

    ProjectException notFound = new ProjectNotFoundException("missing");
    assertEquals("missing", notFound.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, notFound.getStatus());
    assertNull(notFound.getE());
    assertThrows(ProjectNotFoundException.class, () -> {
      throw notFound;
    });
  }

  @Test
  void validatesRequestConstraintsWithoutStartingSpring() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      assertTrue(validator.validate(EmailCreateRequest.builder().body("<p>fine</p>").build())
          .isEmpty());
      assertFalse(validator.validate(EmailCreateRequest.builder().body("<p>broken").build())
          .isEmpty());

      var invalidType = EmailTypeRequest.builder().body("").name("").build();
      assertEquals(2, validator.validate(invalidType).size());
      var validType = EmailTypeRequest.builder()
          .body("<div style='color: red;'>ok</div>").name("News").build();
      assertTrue(validator.validate(validType).isEmpty());
    }
  }

  @Test
  void requestValueEqualityIgnoresOnlyTheExcludedFields() {
    var firstValue = new EmailCreateFieldsValueRequest("subject", "one");
    var secondValue = new EmailCreateFieldsValueRequest("subject", "two");
    assertEquals(firstValue, secondValue);
    assertEquals(firstValue.hashCode(), secondValue.hashCode());
    assertFalse(firstValue.equals(new EmailCreateFieldsValueRequest("to", "two")));

    var optionalField = EmailTypeFieldRequest.builder().name("tracking").build();
    assertFalse(optionalField.isRequired());
    assertEquals(optionalField, new EmailTypeFieldRequest("tracking", true));
  }

  private static ConstraintValidatorContext contextCapturing(AtomicReference<String> message) {
    ConstraintValidatorContext[] context = new ConstraintValidatorContext[1];
    context[0] = (ConstraintValidatorContext) Proxy.newProxyInstance(
        ConstraintValidatorContext.class.getClassLoader(),
        new Class<?>[]{ConstraintValidatorContext.class},
        (proxy, method, arguments) -> {
          if (method.getName().equals("buildConstraintViolationWithTemplate")) {
            message.set((String) arguments[0]);
            return Proxy.newProxyInstance(
                ConstraintValidatorContext.ConstraintViolationBuilder.class.getClassLoader(),
                new Class<?>[]{ConstraintValidatorContext.ConstraintViolationBuilder.class},
                (builder, builderMethod, builderArguments) ->
                    builderMethod.getName().equals("addConstraintViolation") ? context[0] : null);
          }
          return null;
        });
    return context[0];
  }

  private static void assertSaxCause(ThrowingInvocation invocation) {
    var thrown = assertThrows(java.lang.reflect.InvocationTargetException.class,
        invocation::invoke);
    assertTrue(thrown.getCause() instanceof SAXException);
    assertTrue(thrown.getCause().getCause().getMessage().contains("Erro no HTML"));
  }

  @FunctionalInterface
  private interface ThrowingInvocation {
    void invoke() throws Exception;
  }
}
