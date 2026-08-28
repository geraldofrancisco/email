package com.thor.email.domain.request.validation.impl;

import com.thor.email.domain.request.validation.ValidObjectId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

public class ValidObjectIdValidator implements ConstraintValidator<ValidObjectId,String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(StringUtils.isBlank(value)){
      return true;
    }

    return ObjectId.isValid(value);
  }
}
