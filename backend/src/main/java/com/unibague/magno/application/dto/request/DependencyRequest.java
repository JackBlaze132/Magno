package com.unibague.magno.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependencyRequest {

    @NotBlank(message = "Name is required")
    private String name;
}
