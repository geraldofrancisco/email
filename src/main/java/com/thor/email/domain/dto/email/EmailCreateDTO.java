package com.thor.email.domain.dto.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE__MANDATORY_FIELDS_NOT_FILLED_IN;
import static com.thor.email.domain.constants.ProjectConstants.INTERPOLATE_VARIABLE_IN_HTML;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldDTO;
import com.thor.email.domain.exception.ProjectBusinessException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmailCreateDTO extends EmailDTO {

  private List<EmailFieldDTO> filledFields;

  private EmailTypeDTO emailType;

  public void validateFields() {
    var sentFields = this.filledFields.parallelStream()
        .map(EmailFieldDTO::getField)
        .toList();

    var missing = this.emailType.getFields().parallelStream()
        .filter(EmailTypeFieldDTO::isRequired)
        .map(EmailTypeFieldDTO::getName)
        .filter(name -> !sentFields.contains(name))
        .toList();

    if (!missing.isEmpty()) {
      throw new ProjectBusinessException(EMAIL_CREATE__MANDATORY_FIELDS_NOT_FILLED_IN);
    }

  }

  public void generateBody() {
    var originalBody = new AtomicReference<>(this.emailType.getBody());
    this.emailType.getFields().stream()
        .peek(field -> field.setValue(getValueFilledByKey(field.getName())))
        .forEach(field -> {
          var targetKey = String.format(INTERPOLATE_VARIABLE_IN_HTML, field.getName());
          var regex = Pattern.quote(targetKey);
          var safeValue = Matcher.quoteReplacement(field.getValue());
          originalBody.set(originalBody.get().replaceAll(regex, safeValue));
        });
    this.body = originalBody.get();
  }

  private String getValueFilledByKey(String key) {
    return this.filledFields.stream()
        .filter(f -> f.getField().equals(key))
        .findFirst()
        .map(EmailFieldDTO::getValue)
        .orElse(StringUtils.EMPTY);
  }
}
