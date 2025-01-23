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

    @NotBlank(message = "Field name is required")
    private String name;

    /**
     * Notice that more annotations are needed to validate the dates but aren't added
     * like @Past, @Future, @FutureOrPresent, @PastOrPresent
     * It will be added in te future when the application is more advanced
     */
    @NotNull(message = "Field startDate is required")
    private LocalDate startDate;

    @NotNull(message = "Field endDate is required")
    private LocalDate endDate;

    /**
     * This field use the Boolean class instead of the primitive type
     * because if a primitive type is used, the default value will be false in the mapper
     */
    @NotNull(message = "Field isCurrent is required")
    private Boolean isCurrent;
}
