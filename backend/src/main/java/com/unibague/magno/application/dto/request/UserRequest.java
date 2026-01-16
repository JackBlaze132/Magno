package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a user in the system.
 * Contains personal information such as identification, name, and contact details.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {

    @NotBlank(message = "El campo 'full_name' es obligatorio")
    private String fullName;

    @NotBlank(message = "El campo 'identification_number' es obligatorio")
    private String identificationNumber;

    @NotBlank(message = "El campo 'email' es obligatorio")
    @Email(message = "El campo 'email' debe ser un correo válido")
    private String email;

    @NotBlank(message = "El campo 'user_code' es obligatorio")
    private String userCode;

    @NotNull(message = "El campo 'is_external_user' es obligatorio")
    private Boolean isExternalUser;

    @NotNull(message = "El campo 'sex' es obligatorio")
    private Sex sex;

    private TypeOfInternalUser typeOfInternalUser;
}
