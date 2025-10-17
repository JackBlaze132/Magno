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
public class ResearchSeedbedStudentProfileRequest {

    @NotNull(message = "research_seedbed_profile_id is required")
    @Positive(message = "research_seedbed_profile_id must be positive")
    private Long researchSeedbedProfileId;

    /**
     * Although in the database this value refers to the student's profile ID
     * in the request it refers to the student's user ID.
     */
    @NotNull(message = "student_profile_id is required")
    @Positive(message = "student_profile_id must be positive")
    private Long studentProfileId;

    @NotNull(message = "was_active is required")
    private Boolean wasActive;

    @NotNull(message = "is_leader is required")
    private Boolean isLeader;
}
