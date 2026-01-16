package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing detailed student membership information in a research seedbed.
 * Includes full nested objects for the seedbed profile and student profile.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedStudentProfileResponse {

    private Long id;
    private ResearchSeedbedProfileResponse researchSeedbedProfile;
    private StudentProfileResponse studentProfile;
    private Boolean wasActive;
    private Boolean isLeader;
}
