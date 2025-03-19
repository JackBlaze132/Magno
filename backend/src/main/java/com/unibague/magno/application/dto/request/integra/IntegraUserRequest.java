package com.unibague.magno.application.dto.request.integra;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IntegraUserRequest {

    @NotBlank(message = "Field identification is required")
    @Pattern(regexp = "^\\d+$", message = "Identification field must be only numbers between 0-9")
    private String identification;

    @NotNull(message = "Field 'type' is required")
    private JSONIntegraType type;
}
