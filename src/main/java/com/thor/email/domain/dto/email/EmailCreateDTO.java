package com.thor.email.domain.dto.email;

import static com.thor.email.domain.constants.EmailConstants.EMAIL_CREATE__MANDATORY_FIELDS_NOT_FILLED_IN;
import static com.thor.email.domain.constants.ProjectConstants.THYMELEAF_VARIABLE_IN_HTML;

import com.thor.email.domain.dto.email_type.EmailTypeDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldDTO;
import com.thor.email.domain.dto.email_type.EmailTypeFieldSubFieldDTO;
import com.thor.email.domain.enums.FieldTypeFormat;
import com.thor.email.domain.exception.ProjectBusinessException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
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
    if (this.filledFields == null) {
      this.filledFields = new ArrayList<>();
    }

    if (this.emailType == null || this.emailType.getFields() == null) {
      return;
    }

    // 1. Garante que todos os campos/subcampos do schema existam no DTO (definindo "" para ausentes)
    ensureDefaultFields();

    // 2. Acumula os erros de validação dos campos e subcampos via Stream
    List<String> errors = this.emailType.getFields().parallelStream()
        .flatMap(this::validateFieldAndSubfields)
        .toList();

    if (!errors.isEmpty()) {
      throw new ProjectBusinessException(errors);
    }
  }

  private void ensureDefaultFields() {
    this.emailType.getFields().forEach(typeField -> {
      EmailFieldDTO sentField = getOrCreateField(typeField.getName());

      Optional.ofNullable(typeField.getSubFields())
          .orElseGet(Collections::emptyList)
          .forEach(typeSubField -> getOrCreateSubfield(sentField, typeSubField.getName()));
    });
  }

  private Stream<String> validateFieldAndSubfields(EmailTypeFieldDTO typeField) {
    EmailFieldDTO sentField = getOrCreateField(typeField.getName());
    String fieldValue = sentField.getValue();

    // Stream com os erros do campo pai
    Stream<String> parentErrors = validateSingleField(
        typeField.getName(),
        fieldValue,
        typeField.isRequired(),
        typeField.getValueType(),
        "O campo obrigatório '%s' não foi informado."
    );

    // Stream com os erros dos subcampos
    Stream<String> subfieldErrors = Optional.ofNullable(typeField.getSubFields())
        .orElseGet(Collections::emptyList)
        .stream()
        .flatMap(typeSubfield -> validateSubfield(typeField.getName(), typeSubfield, sentField));

    return Stream.concat(parentErrors, subfieldErrors);
  }

  private Stream<String> validateSubfield(String parentName, EmailTypeFieldSubFieldDTO typeSubField,
      EmailFieldDTO sentField) {
    EmailSubfieldDTO sentSubfield = getOrCreateSubfield(sentField, typeSubField.getName());
    String subfieldValue = sentSubfield.getValue();
    String fullName = parentName + "." + typeSubField.getName();

    return validateSingleField(
        fullName,
        subfieldValue,
        typeSubField.isRequired(),
        typeSubField.getValueType(),
        "O subcampo obrigatório '%s' não foi informado."
    );
  }

  private Stream<String> validateSingleField(
      String fieldName,
      String value,
      boolean required,
      FieldTypeFormat valueType,
      String requiredMessageFormat
  ) {
    List<String> errors = new ArrayList<>();

    if (required && StringUtils.isBlank(value)) {
      errors.add(String.format(requiredMessageFormat, fieldName));
    } else if (StringUtils.isNotBlank(value)) {
      validateFormat(fieldName, value, valueType, errors);
    }

    return errors.stream();
  }

  private void validateFormat(String fieldName, String value, FieldTypeFormat valueType,
      List<String> errors) {
    if (valueType != null && StringUtils.isNotBlank(valueType.getRegex())) {
      try {
        if (!Pattern.matches(valueType.getRegex(), value)) {
          errors.add(String.format("O valor '%s' do campo '%s' é inválido para o formato '%s'.",
              value, fieldName, valueType.name()));
        }
      } catch (Exception e) {
        errors.add(String.format("Falha ao processar validação do campo '%s'.", fieldName));
      }
    }
  }

  private EmailFieldDTO getOrCreateField(String name) {
    return this.filledFields.stream()
        .filter(f -> Objects.equals(f.getField(), name))
        .findFirst()
        .orElseGet(() -> {
          EmailFieldDTO newField = EmailFieldDTO.builder()
              .field(name)
              .value(StringUtils.EMPTY)
              .subfields(new ArrayList<>())
              .build();
          this.filledFields.add(newField);
          return newField;
        });
  }

  private EmailSubfieldDTO getOrCreateSubfield(EmailFieldDTO parent, String name) {
    if (parent.getSubfields() == null) {
      parent.setSubfields(new ArrayList<>());
    }
    return parent.getSubfields().stream()
        .filter(sf -> Objects.equals(sf.getField(), name))
        .findFirst()
        .orElseGet(() -> {
          EmailSubfieldDTO newSubfield = EmailSubfieldDTO.builder()
              .field(name)
              .value(StringUtils.EMPTY)
              .build();
          parent.getSubfields().add(newSubfield);
          return newSubfield;
        });
  }

}
