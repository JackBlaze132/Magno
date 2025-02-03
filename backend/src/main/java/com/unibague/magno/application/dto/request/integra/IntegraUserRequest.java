package com.unibague.magno.application.dto.request.integra;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IntegraUserRequest {

    @NotBlank(message = "Field identification is required")
    @Pattern(regexp = "^\\d+$", message = "Identification field must be only numbers between 0-9")
    private String identification;

    @NotNull(message = "Field 'role_ids' is required")
    @Size(min = 1, message = "Field 'role_ids' must have at least one element")
    private Set<Long> roleIds;

    @NotNull(message = "Field 'type' is required")
    private JSONIntegraType type;
}
