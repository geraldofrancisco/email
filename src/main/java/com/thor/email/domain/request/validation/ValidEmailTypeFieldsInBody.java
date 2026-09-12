package com.thor.email.domain.request.validation;

import com.thor.email.domain.request.validation.impl.ValidEmailTypeFieldsInBodyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Constraint(validatedBy = ValidEmailTypeFieldsInBodyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailTypeFieldsInBody {

  String message() default "O campo {fieldName} não foi encontrado no HTML";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
