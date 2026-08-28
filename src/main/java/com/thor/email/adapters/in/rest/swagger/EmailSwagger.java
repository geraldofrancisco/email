package com.thor.email.adapters.in.rest.swagger;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_CREATE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_CREATE_RESPONSE_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_CREATE_SUMMARY;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_TAG_DESCRIPTION;
import static com.thor.email.domain.constants.EmailConstants.EMAIL_CONTROLLER_TAG_NAME;
import static com.thor.email.domain.constants.ProjectConstants.PROJECT_SWAGGER_STATUS_CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.response.email.EmailCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = EMAIL_CONTROLLER_TAG_NAME, description = EMAIL_CONTROLLER_TAG_DESCRIPTION)
public interface EmailSwagger {

  @Operation(
      summary = EMAIL_CONTROLLER_CREATE_SUMMARY,
      description = EMAIL_CONTROLLER_CREATE_DESCRIPTION,
      responses =
      @ApiResponse(
          responseCode = PROJECT_SWAGGER_STATUS_CREATED,
          description = EMAIL_CONTROLLER_CREATE_RESPONSE_DESCRIPTION,
          content =
          @Content(
              mediaType = APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = EmailCreateResponse.class))))
  EmailCreateResponse create(@RequestBody @Valid final EmailCreateRequest request);
}
