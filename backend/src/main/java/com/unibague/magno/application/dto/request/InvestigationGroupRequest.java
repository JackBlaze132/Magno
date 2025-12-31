package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.LineOfResearch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestigationGroupRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;

    @NotNull(message = "El campo 'lines_of_research' es obligatorio")
    @Size(min = 2, max = 5, message = "El campo 'lines_of_research' debe tener entre 2 y 5 elementos")
    private Set<LineOfResearch> linesOfResearch;
}
