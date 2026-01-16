package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.LineOfResearch;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response DTO containing investigation group information.
 * Includes the group's identifier, name, and assigned lines of research.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestigationGroupResponse {

    private Long id;
    private String name;
    private Set<LineOfResearch> linesOfResearch;
}
