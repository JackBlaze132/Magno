package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcademicProgramRequest {

    @NotBlank(message = "Field name is required")
    private String name;

    @NotBlank(message = "Field code is required")
    private String programCode;

    @NotNull(message = "Field type is required")
    AcademicProgramType type;
}
