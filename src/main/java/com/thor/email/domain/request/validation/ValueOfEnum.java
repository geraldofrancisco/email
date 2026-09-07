package com.thor.email.domain.request.validation;


import com.thor.email.domain.request.validation.impl.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@ReportAsSingleViolation
public @interface ValueOfEnum {
  Class<? extends Enum> enumClass();

  String[] excluded() default {};

  String message() default "Value is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
