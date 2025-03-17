package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcademicPeriodResponse {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    
    @JsonProperty("is_current")
    private boolean isCurrent;
}
