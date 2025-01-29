package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {

    @NotBlank(message = "Field 'full_name' is required")
    private String fullName;

    @NotBlank(message = "Field 'identification_number' is required")
    private String identificationNumber;

    @NotBlank(message = "Field 'email' is required")
    @Email(message = "Field 'email' must be a valid email")
    private String email;

    @NotBlank(message = "Field 'user_code' is required")
    private String userCode;

    @NotNull(message = "Field 'is_external_user' is required")
    private boolean isExternalUser;

    @NotNull(message = "Field 'sex' is required")
    Sex sex;

    @NotNull(message = "Field 'role_ids' is required")
    @Size(min = 1, message = "Field 'role_ids' must have at least one element")
    private Set<Long> roleIds;
}
