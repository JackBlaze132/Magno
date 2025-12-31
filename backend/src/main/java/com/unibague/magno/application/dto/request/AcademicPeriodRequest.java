package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcademicPeriodRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;

    @NotNull(message = "El campo 'start_date' es obligatorio")
    private LocalDate startDate;

    @NotNull(message = "El campo 'end_date' es obligatorio")
    private LocalDate endDate;

    /**
     * This field use the Boolean class instead of the primitive type
     * because if a primitive type is used, the default value will be false in the mapper
     */
    @NotNull(message = "El campo 'is_current' es obligatorio")
    private Boolean isCurrent;
}
