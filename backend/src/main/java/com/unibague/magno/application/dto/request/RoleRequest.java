package com.unibague.magno.application.dto.request;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    @NotNull(message = "Field name is required")
    private SeedbedRole name;

    @NotBlank(message = "Field description is required")
    private String description;
}
