package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing investigation group profile information.
 * Represents the configuration of an investigation group for a specific academic period,
 * including its coordinator assignment.
 */
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestigationGroupProfileResponse {

    private Long id;
    private InvestigationGroupResponse investigationGroup;
    private FunctionaryProfileResponse coordinator;
    private AcademicPeriodResponse academicPeriod;
}
