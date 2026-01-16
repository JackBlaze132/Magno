package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for generating a participation certificate for a student.
 * The certificate confirms a student's membership and participation
 * in a specific research seedbed.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentSeedbedCertificateRequest {

    @NotNull(message = "El campo 'user_id' es obligatorio")
    @Positive(message = "El campo 'user_id' debe ser un número positivo")
    private Long userId;

    @NotNull(message = "El campo 'research_seedbed_id' es obligatorio")
    @Positive(message = "El campo 'research_seedbed_id' debe ser un número positivo")
    private Long researchSeedbedId;
}
