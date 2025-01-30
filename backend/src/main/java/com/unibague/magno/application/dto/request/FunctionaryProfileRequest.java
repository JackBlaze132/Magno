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
public class FunctionaryProfileRequest {

    @NotNull(message = "Field userId is required")
    @Positive(message = "Field userId must be positive")
    private Long userId;

    @NotNull(message = "Field academicPeriodId is required")
    @Positive(message = "Field academicPeriodId must be positive")
    private Long academicPeriodId;

    @NotNull(message = "Field dependencyId is required")
    @Positive(message = "Field dependencyId must be positive")
    private Long dependencyId;
}
