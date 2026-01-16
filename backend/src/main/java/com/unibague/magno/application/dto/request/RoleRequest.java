package com.unibague.magno.application.dto.request;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a role within the system.
 * Roles define the permissions and responsibilities of users
 * in research groups and seedbeds.
 */
@Getter
@Setter
public class RoleRequest {

    @NotNull(message = "El campo 'name' es obligatorio")
    private SeedbedRole name;

    @NotBlank(message = "El campo 'description' es obligatorio")
    private String description;
}
