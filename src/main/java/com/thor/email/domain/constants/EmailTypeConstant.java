package com.thor.email.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeConstant {

  public static final String EMAIL_TYPE_TABLE_NAME = "tipo-email";

  public static final String EMAIL_TYPE_REQUEST_BODY_REQUIRED = "EMAIL_TYPE_REQUEST_BODY_REQUIRED";
  public static final String EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS = "EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS";
  public static final String EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED = "EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED";

  public static final String EMAIL_TYPE_REQUEST_BODY_DESCRIPTION = "HTML to be sent via email, containing the variables declared in the 'field.name' field. This field must use the @@name@@ format to allow for subsequent variable substitution.";
  public static final String EMAIL_TYPE_REQUEST_FIELDS_DESCRIPTION = "Fields to be used in the email. They must exist in the HTML, even if they are not required. If a field is specified that is not declared in the body, the request will return an error.";
  public static final String EMAIL_TYPE_FIELD_REQUEST_NAME_DESCRIPTION = "Name of the field that must exist in the HTML";
  public static final String EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION = "Check for mandatory field completion; if the field is not mandatory and is left blank when the email is sent, the default value is an empty string.";
}
