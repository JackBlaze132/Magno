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
public class ResearchSeedbedProfileSummaryResponse {

    private Long id;
    private String researchSeedbedName;
    private String coordinatorName;
    private String tutorName;
    private String investigationGroupName;
    private String academicPeriodName;
    private Boolean wasActive;
}
