package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a research seedbed profile.
 * Represents the configuration of a research seedbed for a specific academic period,
 * including its coordinator, tutor, and parent investigation group.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchSeedbedProfileRequest {

    @NotNull(message = "El campo 'research_seedbed_id' es obligatorio")
    @Positive(message = "El campo 'research_seedbed_id' debe ser un número positivo")
    private Long researchSeedbedId;

    /**
     * Although in the database this value refers to the ID of the functionary_profile, for UX reasons
     * the user_id must be sent in the request.
     */
    @NotNull(message = "El campo 'coordinator_id' es obligatorio")
    @Positive(message = "El campo 'coordinator_id' debe ser un número positivo")
    private Long coordinatorId;

    /**
     * Although in the database this value refers to the ID of the functionary_profile, for UX reasons
     * the user_id must be sent in the request.
     */
    @Positive(message = "El campo 'tutor_id' debe ser un número positivo")
    private Long tutorId;

    @NotNull(message = "El campo 'investigation_group_profile_id' es obligatorio")
    @Positive(message = "El campo 'investigation_group_profile_id' debe ser un número positivo")
    private Long investigationGroupProfileId;

    @NotNull(message = "El campo 'academic_period_id' es obligatorio")
    @Positive(message = "El campo 'academic_period_id' debe ser un número positivo")
    private Long academicPeriodId;

    @NotNull(message = "El campo 'was_active' es obligatorio")
    private Boolean wasActive;
}
