package com.thor.email.domain.dto.email;

import com.thor.email.domain.dto.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmailPageDTO  extends PageDTO<EmailDTO> {

}
