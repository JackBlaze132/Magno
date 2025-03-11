package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedStudentProfileRequest {

    @NotNull(message = "research_seedbed_profile_id is required")
    @Positive(message = "research_seedbed_profile_id must be positive")
    private Long researchSeedbedProfileId;

    @NotNull(message = "student_profile_id is required")
    @Positive(message = "student_profile_id must be positive")
    private Long studentProfileId;

    @NotNull(message = "was_active is required")
    private Boolean wasActive;

    @NotNull(message = "is_leader is required")
    private Boolean isLeader;
}
