package com.thor.email.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeConstant {
  public static final String EMAIL_TYPE_TABLE_NAME = "tipo-email";

  public static final String EMAIL_TYPE_REQUEST_BODY_REQUIRED = "EMAIL_TYPE_REQUEST_BODY_REQUIRED";
  public static final String EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS = "EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS";
  public static final String EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED = "EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED";
}
