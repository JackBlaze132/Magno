package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating an investigation group profile.
 * Represents the configuration of an investigation group for a specific academic period,
 * including its assigned coordinator.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestigationGroupProfileRequest {

    @NotNull(message = "El campo 'investigation_group_id' es obligatorio")
    @Positive(message = "El campo 'investigation_group_id' debe ser un número positivo")
    private Long investigationGroupId;

    /**
     * The user ID of the coordinator to be assigned.
     * Note: Although the domain model stores a functionary_profile ID,
     * this field accepts a user ID for better UX. The service layer
     * handles the conversion to functionary_profile ID.
     */
    @NotNull(message = "El campo 'coordinator_id' es obligatorio")
    @Positive(message = "El campo 'coordinator_id' debe ser un número positivo")
    private Long coordinatorId;

    @NotNull(message = "El campo 'academic_period_id' es obligatorio")
    @Positive(message = "El campo 'academic_period_id' debe ser un número positivo")
    private Long academicPeriodId;
}
