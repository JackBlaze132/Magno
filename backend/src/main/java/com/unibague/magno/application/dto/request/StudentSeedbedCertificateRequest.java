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
public class StudentSeedbedCertificateRequest {

    @NotNull(message = "The userId field cannot be null")
    @Positive(message = "The userId field must be a positive number")
    private Long userId;

    @NotNull(message = "The researchSeedbedId field cannot be null")
    @Positive(message = "The researchSeedbedId field must be a positive number")
    private Long researchSeedbedId;
}
