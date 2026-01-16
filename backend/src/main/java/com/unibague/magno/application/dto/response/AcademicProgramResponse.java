package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing academic program information.
 * Includes the program's identifier, name, code, and type classification.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcademicProgramResponse {

    private Long id;
    private String name;
    private String programCode;
    private AcademicProgramType type;
}
