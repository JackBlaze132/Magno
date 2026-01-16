package com.unibague.magno.application.dto.request.integra;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for querying user information from the Integra system.
 * Integra is the university's external information system that provides
 * data about students, functionaries, academic programs, and dependencies.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IntegraUserRequest {

    @NotBlank(message = "El campo 'identification' es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El campo 'identification' debe contener solo números")
    private String identification;

    @NotNull(message = "El campo 'type' es obligatorio")
    private JSONIntegraType type;
}
