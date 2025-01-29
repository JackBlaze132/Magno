package com.unibague.magno.application.dto.request;

import com.unibague.magno.domain.model.LineOfResearch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class InvestigationGroupRequest {

    @NotBlank(message = "Name field is required")
    private String name;

    @NotNull(message = "Line of research field is required")
    @Size(min = 2, max = 5, message = "Line of research field must have between 2 and 5 elements")
    private Set<LineOfResearch> linesOfResearch;
}
