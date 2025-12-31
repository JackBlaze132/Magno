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

    @NotNull(message = "El campo 'research_seedbed_profile_id' es obligatorio")
    @Positive(message = "El campo 'research_seedbed_profile_id' debe ser un número positivo")
    private Long researchSeedbedProfileId;

    /**
     * Although in the database this value refers to the student's profile ID
     * in the request it refers to the student's user ID.
     */
    @NotNull(message = "El campo 'student_profile_id' es obligatorio")
    @Positive(message = "El campo 'student_profile_id' debe ser un número positivo")
    private Long studentProfileId;

    @NotNull(message = "El campo 'was_active' es obligatorio")
    private Boolean wasActive;

    @NotNull(message = "El campo 'is_leader' es obligatorio")
    private Boolean isLeader;
}
