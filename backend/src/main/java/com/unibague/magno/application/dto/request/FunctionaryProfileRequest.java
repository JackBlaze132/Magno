package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FunctionaryProfileRequest {

    @NotNull(message = "Field user_id is required")
    @Positive(message = "Field user_id must be positive")
    private Long userId;

    @NotNull(message = "Field academic_period_id is required")
    @Positive(message = "Field academic_period_id must be positive")
    private Long academicPeriodId;

    @NotNull(message = "Field 'role_id' is required")
    @Positive(message = "Field role_id must be positive")
    private Long roleId;
}
