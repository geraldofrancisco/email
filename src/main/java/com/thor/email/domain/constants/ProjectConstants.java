package com.thor.email.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConstants {

  public static final String INTERPOLATE_VARIABLE_IN_HTML = "{{%s}}";

  public static final String PROJECT_SWAGGER_STATUS_OK = "200";
  public static final String PROJECT_SWAGGER_STATUS_CREATED = "201";

  public static final String DEFAULT_PAGE_SIZE_VALUE = "25";

  public static final String MONGO_ID_EXAMPLE = "6a8a26b048b96cd0f266d835";
  public static final String MONGO_ID_NAME = "id";


  public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String DATE_PATTERN = "yyyy-MM-dd";

  public static final String PROJECT_GENERIC_EXCEPTION = "PROJECT_GENERIC_EXCEPTION";

  public static final String PROJECT_EXCEPTION_FIELD_RESPONSE_NAME_DESCRIPTION = "Field name exception";
  public static final String PROJECT_EXCEPTION_FIELD_RESPONSE_MESSAGE_DESCRIPTION = "Message describing the reason for the field exception";
  public static final String PROJECT_EXCEPTION_RESPONSE_TIMESTAMP_DESCRIPTION = "Date time of exception";
  public static final String PROJECT_EXCEPTION_RESPONSE_ERROR_DESCRIPTION = "Description of exception";
  public static final String PROJECT_EXCEPTION_RESPONSE_LIST_FIELDS_DESCRIPTION = "List with description of fields that have an error";
  public static final String PROJECT_EXCEPTION_RESPONSE_HTTP_STATUS_DESCRIPTION = "Http response status";

  public static final String UNIQUE_IDENTIFIER_DESCRIPTION = "Unique system identifier";
  public static final String DATE_TIME_CREATION_REGISTER_DESCRIPTION = "Record creation date/time";
  public static final String PAGE_RESPONSE_CONTENT_DESCRIPTION = "Pagination content";
  public static final String PAGE_RESPONSE_HAS_NEXT_DESCRIPTION = "There is a next page.";
  public static final String PAGE_RESPONSE_NEXT_POSITION_DESCRIPTION = "Next position to be sought";
}
