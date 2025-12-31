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

    @NotNull(message = "El campo 'user_id' es obligatorio")
    @Positive(message = "El campo 'user_id' debe ser un número positivo")
    private Long userId;

    @NotNull(message = "El campo 'academic_period_id' es obligatorio")
    @Positive(message = "El campo 'academic_period_id' debe ser un número positivo")
    private Long academicPeriodId;

    @NotNull(message = "El campo 'role_id' es obligatorio")
    @Positive(message = "El campo 'role_id' debe ser un número positivo")
    private Long roleId;
}
