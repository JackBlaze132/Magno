package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing detailed research seedbed profile information.
 * Includes full nested objects for the seedbed, coordinator, tutor,
 * investigation group profile, and academic period.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedProfileResponse {

    private Long id;
    private ResearchSeedbedResponse researchSeedbed;
    private FunctionaryProfileResponse coordinator;
    private FunctionaryProfileResponse tutor;
    private InvestigationGroupProfileResponse investigationGroupProfile;
    private AcademicPeriodResponse academicPeriod;
    private Boolean wasActive;
}
