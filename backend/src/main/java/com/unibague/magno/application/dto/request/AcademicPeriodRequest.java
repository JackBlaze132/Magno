package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Request DTO for creating or updating an academic period.
 * Contains the period's name, date range, and current status flag.
 */
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
     * Indicates whether this is the currently active academic period.
     * Uses Boolean wrapper class instead of primitive to avoid default false
     * value during object mapping.
     */
    @NotNull(message = "El campo 'is_current' es obligatorio")
    private Boolean isCurrent;
}
