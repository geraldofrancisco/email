package com.thor.email.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTypeConstants {

  public static final String EMAIL_TYPE_TABLE_NAME = "tipo-email";
  public static final String EMAIL_TYPE_CREATION_DATETIME_FIELD = "data-hora-criacao";
  public static final String EMAIL_TYPE_BODY_FIELD = "modelo";
  public static final String EMAIL_TYPE_NAME_FIELD = "nome";
  public static final String EMAIL_TYPE_FIELDS_FIELD = "campos";
  public static final String EMAIL_TYPE_FIELD_NAME_FIELD = "nome";
  public static final String EMAIL_TYPE_FIELD_REQUIRED_FIELD = "requerido";

  public static final String EMAIL_TYPE_CONTROLLER_TAG_NAME = "Email Type Controller";
  public static final String EMAIL_TYPE_CONTROLLER_TAG_DESCRIPTION = "Controller for types of emails that can be sent.";

  public static final String EMAIL_TYPE_CONTROLLER_CREATE_SUMMARY = "Create type";
  public static final String EMAIL_TYPE_CONTROLLER_CREATE_DESCRIPTION = "Controller for create types of emails that can be sent.";
  public static final String EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_DESCRIPTION = "Email type creation identifier";
  public static final String EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_ID = "Unique identifier assigned by the system to locate the created email type.";
  public static final String EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_NAME = "Name assigned by the requester to facilitate locating the model.";

  public static final String EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_SUMMARY = "Get all created emails.";
  public static final String EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_DESCRIPTION = "Get all created emails in a paginated manner.";
  public static final String EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_RESPONSE_DESCRIPTION = "Returns a paginated list of registered email types.";
  public static final String EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_QUERY_PARAM_NAME_DESCRIPTION = "Optional parameter for lookup by email name";



  public static final String EMAIL_TYPE_REQUEST_BODY_REQUIRED = "EMAIL_TYPE_REQUEST_BODY_REQUIRED";
  public static final String EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS = "EMAIL_TYPE_REQUEST_BODY_CONTAINS_ALL_FIELDS";
  public static final String EMAIL_TYPE_REQUEST_NAME_REQUIRED = "EMAIL_TYPE_REQUEST_NAME_REQUIRED";
  public static final String EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED = "EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED";

  public static final String EMAIL_TYPE_BODY_DESCRIPTION = "HTML to be sent via email, containing the variables declared in the 'field.name' field. This field must use the {{name}} format to allow for subsequent variable substitution. Replace double quotes with single quotes and use the minified code.";
  public static final String EMAIL_TYPE_NAME_DESCRIPTION = "Name given to the email type to make it easier to search for and identify which type to send.";
  public static final String EMAIL_TYPE_FIELDS_DESCRIPTION = "Fields to be used in the email. They must exist in the HTML, even if they are not required. If a field is specified that is not declared in the body, the request will return an error.";
  public static final String EMAIL_TYPE_FIELD_NAME_DESCRIPTION = "Name of the field that must exist in the HTML.";
  public static final String EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION = "Check for mandatory field completion; if the field is not mandatory and is left blank when the email is sent, the default value is an empty string.";
}
