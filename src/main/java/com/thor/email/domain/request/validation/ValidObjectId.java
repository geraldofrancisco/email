package com.thor.email.domain.request.validation;

import com.thor.email.domain.request.validation.impl.HTMLContentValidator;
import com.thor.email.domain.request.validation.impl.ValidObjectIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidObjectIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidObjectId {
  String message() default "Conteúdo HTML inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
