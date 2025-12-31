package com.unibague.magno.application.dto.request;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    @NotNull(message = "El campo 'name' es obligatorio")
    private SeedbedRole name;

    @NotBlank(message = "El campo 'description' es obligatorio")
    private String description;
}
