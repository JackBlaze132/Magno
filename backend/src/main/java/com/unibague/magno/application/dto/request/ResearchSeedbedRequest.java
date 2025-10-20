package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.LineOfResearch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedRequest {

    @NotBlank(message = "Field name is required")
    private String name;

    @NotBlank(message = "Field vision is required")
    private String mission;

    @NotBlank(message = "Field vision is required")
    private String vision;

    @NotBlank(message = "Field research_proposal_description is required")
    private String researchProposalDescription;

    @NotNull(message = "Field creation_date is required")
    @PastOrPresent(message = "Field creation_date must be a past or present date")
    private LocalDate creationDate;

    @NotNull(message = "Field line_of_research is required")
    private LineOfResearch lineOfResearch;
}
