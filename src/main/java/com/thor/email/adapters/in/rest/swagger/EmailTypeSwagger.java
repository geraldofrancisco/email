package com.thor.email.adapters.in.rest.swagger;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_CREATE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_CREATE_SUMMARY;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_QUERY_PARAM_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_RESPONSE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_SUMMARY;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_TAG_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_CONTROLLER_TAG_NAME;
import static com.thor.email.domain.constants.ProjectConstants.DEFAULT_PAGE_SIZE_VALUE;
import static com.thor.email.domain.constants.ProjectConstants.HEADER_GET_BY_FILTER_CURSOR_DESCRIPTION;
import static com.thor.email.domain.constants.ProjectConstants.PROJECT_SWAGGER_STATUS_CREATED;
import static com.thor.email.domain.constants.ProjectConstants.PROJECT_SWAGGER_STATUS_OK;
import static com.thor.email.domain.constants.ProjectConstants.QUERY_GET_BY_FILTER_SIZE_DESCRIPTION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.thor.email.domain.request.email_type.EmailTypeRequest;
import com.thor.email.domain.response.email_type.EmailTypeCreateResponse;
import com.thor.email.domain.response.email_type.EmailTypePageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = EMAIL_TYPE_CONTROLLER_TAG_NAME, description = EMAIL_TYPE_CONTROLLER_TAG_DESCRIPTION)
public interface EmailTypeSwagger {

  @Operation(
      summary = EMAIL_TYPE_CONTROLLER_CREATE_SUMMARY,
      description = EMAIL_TYPE_CONTROLLER_CREATE_DESCRIPTION,
      responses =
      @ApiResponse(
          responseCode = PROJECT_SWAGGER_STATUS_CREATED,
          description = EMAIL_TYPE_CONTROLLER_CREATE_RESPONSE_DESCRIPTION,
          content =
          @Content(
              mediaType = APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = EmailTypeCreateResponse.class))))
  EmailTypeCreateResponse create(@RequestBody @Valid final EmailTypeRequest request);

  @Operation(
      summary = EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_SUMMARY,
      description = EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_DESCRIPTION,
      responses =
      @ApiResponse(
          responseCode = PROJECT_SWAGGER_STATUS_OK,
          description = EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_RESPONSE_DESCRIPTION,
          content =
          @Content(
              mediaType = APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = EmailTypePageResponse.class))))
  EmailTypePageResponse getByFilter(
      @RequestParam(required = false) @Parameter(description = EMAIL_TYPE_CONTROLLER_GET_BY_FILTER_QUERY_PARAM_NAME_DESCRIPTION) String name,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_VALUE) @Parameter(description = QUERY_GET_BY_FILTER_SIZE_DESCRIPTION) Integer size,
      @RequestHeader(required = false) @Parameter(description = HEADER_GET_BY_FILTER_CURSOR_DESCRIPTION) String cursor
  );
}
