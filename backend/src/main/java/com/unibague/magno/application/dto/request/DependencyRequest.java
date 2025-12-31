package com.unibague.magno.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependencyRequest {

    @NotBlank(message = "El campo 'name' es obligatorio")
    private String name;
}
