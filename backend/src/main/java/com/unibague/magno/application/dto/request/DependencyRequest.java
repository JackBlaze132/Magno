package com.unibague.magno.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a university dependency (faculty or department).
 */
@Getter
@Setter
public class DependencyRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;
}
