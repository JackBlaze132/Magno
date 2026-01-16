package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing a summarized view of a student's membership in a research seedbed.
 * Uses summarized seedbed profile data instead of full nested objects,
 * useful for list views displaying student participation across seedbeds.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedStudentProfileSummaryResponse {

    private Long id;
    private ResearchSeedbedProfileSummaryResponse researchSeedbedProfile;
    private StudentProfileResponse studentProfile;
    private Boolean wasActive;
    private Boolean isLeader;
}
