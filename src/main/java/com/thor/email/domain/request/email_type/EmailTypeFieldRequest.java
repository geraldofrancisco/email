package com.thor.email.domain.request.email_type;

import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_NAME_DESCRIPTION;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED;
import static com.thor.email.domain.constants.EmailTypeConstants.EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thor.email.domain.enums.FieldType;
import com.thor.email.domain.request.email.EmailCreateRequest;
import com.thor.email.domain.request.validation.SecondValidationGroup;
import com.thor.email.domain.request.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "required")
@GroupSequence({EmailTypeFieldRequest.class, SecondValidationGroup.class})
public class EmailTypeFieldRequest {

  @Schema(description = EMAIL_TYPE_FIELD_NAME_DESCRIPTION)
  @NotEmpty(message = EMAIL_TYPE_FIELD_REQUEST_NAME_REQUIRED)
  private String name;

  @Schema(description = EMAIL_TYPE_FIELD_REQUEST_REQUIRED_DESCRIPTION)
  private boolean required = true;

  @Schema(description = "Tipo do campo: SIMPLE para variáveis simples ou LIST para coleções iteráveis")
  @NotBlank(message = "o tipo deve ser preenchido")
  @ValueOfEnum(enumClass = FieldType.class, message = "tipo inválido")
  private String type;

  @Schema(description = "Atributos internos dos itens da lista (Obrigatório se type = LIST)")
  private Set<@Valid EmailTypeFieldSubFieldRequest> subFields = new HashSet<>();

  @Hidden
  @JsonIgnore
  @AssertTrue(message = "Campos do tipo LIST devem conter pelo menos um subcampo (subFields).", groups = SecondValidationGroup.class)
  public boolean isHasSubFieldsIfList() {
    if (FieldType.LIST.name().equals(type)) {
      return Objects.nonNull(subFields) && !subFields.isEmpty();
    }
    return true;
  }

  @Hidden
  @JsonIgnore
  @AssertTrue(message = "Campos do tipo SIMPLE deve estar vazio (subFields).", groups = SecondValidationGroup.class)
  public boolean isMustBeNullIfTheTypeIsSimple() {
    return !FieldType.SIMPLE.name().equals(type) || Objects.isNull(subFields) || subFields.isEmpty();
  }
}
