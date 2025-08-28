package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoleResponse {

    private Long id;
    private SeedbedRole name;
    private String description;
}
