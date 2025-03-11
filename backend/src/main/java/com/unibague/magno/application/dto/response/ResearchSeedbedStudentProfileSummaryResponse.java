package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
