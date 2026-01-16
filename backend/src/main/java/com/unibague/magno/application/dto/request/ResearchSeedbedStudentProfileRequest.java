package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a student's membership in a research seedbed.
 * Links a student to a specific research seedbed profile for a given academic period.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedStudentProfileRequest {

    @NotNull(message = "El campo 'research_seedbed_profile_id' es obligatorio")
    @Positive(message = "El campo 'research_seedbed_profile_id' debe ser un número positivo")
    private Long researchSeedbedProfileId;

    /**
     * The user ID of the student to be enrolled.
     * Note: Although the domain model stores a student_profile ID,
     * this field accepts a user ID for better UX. The service layer
     * handles the conversion to student_profile ID.
     */
    @NotNull(message = "El campo 'student_profile_id' es obligatorio")
    @Positive(message = "El campo 'student_profile_id' debe ser un número positivo")
    private Long studentProfileId;

    @NotNull(message = "El campo 'was_active' es obligatorio")
    private Boolean wasActive;

    @NotNull(message = "El campo 'is_leader' es obligatorio")
    private Boolean isLeader;
}
