package com.thor.email.domain.response.email_type;

import com.thor.email.domain.response.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmailTypePageResponse extends PageResponse<EmailTypeResponse> {

}
