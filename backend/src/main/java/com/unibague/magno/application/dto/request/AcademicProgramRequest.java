package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating an academic program.
 * Contains the program's name, unique code, and type classification.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcademicProgramRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;

    @NotBlank(message = "El campo 'program_code' es obligatorio")
    private String programCode;

    @NotNull(message = "El campo 'type' es obligatorio")
    AcademicProgramType type;
}
