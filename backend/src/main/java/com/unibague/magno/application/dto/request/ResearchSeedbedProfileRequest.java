package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedProfileRequest {

    @NotNull(message = "research_seedbed_id is required")
    @Positive(message = "research_seedbed_id must be positive")
    private Long researchSeedbedId;

    @NotNull(message = "coordinator_id is required")
    @Positive(message = "coordinator_id must be positive")
    private Long coordinatorId;

    @Positive(message = "tutor_id must be positive")
    private Long tutorId;

    @NotNull(message = "investigation_group_profile_id is required")
    @Positive(message = "investigation_group_profile_id must be positive")
    private Long investigationGroupProfileId;

    @NotNull(message = "academic_period_id is required")
    @Positive(message = "academic_period_id must be positive")
    private Long academicPeriodId;

    @NotNull(message = "was_active is required")
    private Boolean wasActive;
}
