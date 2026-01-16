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

/**
 * Request DTO for creating or updating a research seedbed.
 * Research seedbeds are student research groups that operate under
 * an investigation group, focusing on a specific line of research.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;

    @NotBlank(message = "El campo 'mission' es obligatorio")
    private String mission;

    @NotBlank(message = "El campo 'vision' es obligatorio")
    private String vision;

    @NotBlank(message = "El campo 'research_proposal_description' es obligatorio")
    private String researchProposalDescription;

    @NotNull(message = "El campo 'creation_date' es obligatorio")
    @PastOrPresent(message = "El campo 'creation_date' debe ser una fecha pasada o presente")
    private LocalDate creationDate;

    @NotNull(message = "El campo 'line_of_research' es obligatorio")
    private LineOfResearch lineOfResearch;
}
