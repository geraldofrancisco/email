package com.thor.email.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConstants {

  public static final String EMAIL_TABLE_NAME = "email";
  public static final String EMAIL_TYPE_ID = "tipo-email-id";
  public static final String EMAIL_CREATION_DATETIME_FIELD = "data-hora-criacao";
  public static final String EMAIL_TITLE_FIELD = "titulo";
  public static final String EMAIL_BODY_FIELD = "corpo";
  public static final String EMAIL_TO_FIELD = "para";
  public static final String EMAIL_CCO_FIELD = "copia-oculta";
  public static final String EMAIL_TIMESTAMP_SEND_DATE_FIELD = "data-hora-envio";

  public static final String EMAIL_CONTROLLER_TAG_NAME = "Email Controller";
  public static final String EMAIL_CONTROLLER_TAG_DESCRIPTION = "Controller to ensure emails are effectively created and sent when necessary.";

  public static final String EMAIL_CONTROLLER_CREATE_SUMMARY = "Create body";
  public static final String EMAIL_CONTROLLER_CREATE_DESCRIPTION = "Controller for create  of emails that can be sent.";
  public static final String EMAIL_CONTROLLER_CREATE_RESPONSE_DESCRIPTION = "Email type creation identifier and body created";
  public static final String EMAIL_CONTROLLER_REQUEST_EMAIL_TYPE_ID_DESCRIPTION = "Unique identifier assigned by the system to locate the created email type.";
  public static final String EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_DESCRIPTION = "Values for the fields defined in the email type. All fields declared as required must be provided.";
  public static final String EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_FIELD_DESCRIPTION = "Name of the field declared in the email type";
  public static final String EMAIL_CONTROLLER_REQUEST_FIELD_VALUES_VALUE_DESCRIPTION = "Declared field value";
  public static final String EMAIL_CONTROLLER_CREATE_RESPONSE_ID = "Unique identifier assigned by the system to locate the created email.";
  public static final String EMAIL_CONTROLLER_CREATE_RESPONSE_BODY = "Email body created";

  public static final String EMAIL_CREATE__REQUEST_EMAIL_TYPE_ID_REQUIRED = "EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_REQUIRED";
  public static final String EMAIL_CREATE__REQUEST_EMAIL_TYPE_ID_INVALID = "EMAIL_CREATE_REQUEST_EMAIL_TYPE_ID_INVALID";
  public static final String EMAIL_CREATE__REQUEST_FIELD_VALUES_FIELD_REQUIRED = "EMAIL_CREATE_REQUEST_FIELD_VALUES_FIELD_REQUIRED";
  public static final String EMAIL_CREATE__REQUEST_FIELD_VALUES_VALUE_REQUIRED = "EMAIL_CREATE_REQUEST_FIELD_VALUES_VALUE_REQUIRED";
  public static final String EMAIL_CREATE__EMAIL_TYPE_NOT_FOUND = "EMAIL_CREATE__EMAIL_TYPE_NOT_FOUND";
  public static final String EMAIL_CREATE__MANDATORY_FIELDS_NOT_FILLED_IN = "EMAIL_CREATE__MANDATORY_FIELDS_NOT_FILLED_IN";

}
