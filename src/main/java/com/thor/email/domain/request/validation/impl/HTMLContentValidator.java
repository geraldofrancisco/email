package com.thor.email.domain.request.validation.impl;

import com.thor.email.domain.request.validation.ValidHTML;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

public class HTMLContentValidator implements ConstraintValidator<ValidHTML, String> {

  private final TemplateEngine templateEngine;

  public HTMLContentValidator() {
    StringTemplateResolver resolver = new StringTemplateResolver();
    resolver.setTemplateMode("HTML");

    this.templateEngine = new TemplateEngine();
    this.templateEngine.setTemplateResolver(resolver);
  }

  @Override
  public boolean isValid(String html, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(html)) {
      return true;
    }
    try {

      templateEngine.process(html, new Context());
      return true;
    } catch (Exception e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Sintaxe Thymeleaf inválida: " + e.getMessage())
          .addConstraintViolation();
      return false;
    }
  }

}