package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing role information.
 * Roles define the permissions and responsibilities of users
 * in research groups and seedbeds.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoleResponse {

    private Long id;
    private String name;
    private String description;
}
