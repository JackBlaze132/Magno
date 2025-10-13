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

    @NotNull(message = "Field investigation_group_id is required")
    @Positive(message = "Field investigation_group_id must be positive")
    private Long investigationGroupId;

    /**
     * Although in the model this field refers to the id of a functionary_profile, when the request is made,
     * it refers to the user_id for UX reasons. This will be handled in the service layer.
     */
    @NotNull(message = "Field coordinator_id is required")
    @Positive(message = "Field coordinator_id must be positive")
    private Long coordinatorId;

    @NotNull(message = "Field academic_period_id is required")
    @Positive(message = "Field academic_period_id must be positive")
    private Long academicPeriodId;
}
