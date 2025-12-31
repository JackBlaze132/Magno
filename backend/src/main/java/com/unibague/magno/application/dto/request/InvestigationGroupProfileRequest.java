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
public class InvestigationGroupProfileRequest {

    @NotNull(message = "El campo 'investigation_group_id' es obligatorio")
    @Positive(message = "El campo 'investigation_group_id' debe ser un número positivo")
    private Long investigationGroupId;

    /**
     * Although in the model this field refers to the id of a functionary_profile, when the request is made,
     * it refers to the user_id for UX reasons. This will be handled in the service layer.
     */
    @NotNull(message = "El campo 'coordinator_id' es obligatorio")
    @Positive(message = "El campo 'coordinator_id' debe ser un número positivo")
    private Long coordinatorId;

    @NotNull(message = "El campo 'academic_period_id' es obligatorio")
    @Positive(message = "El campo 'academic_period_id' debe ser un número positivo")
    private Long academicPeriodId;
}
