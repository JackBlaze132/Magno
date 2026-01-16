package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing functionary profile information.
 * Includes the functionary's user data, academic period, dependency, and assigned role.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FunctionaryProfileResponse {

    private Long id;
    private UserResponse user;
    private AcademicPeriodResponse academicPeriod;
    private DependencyResponse dependency;
    private RoleResponse role;
}
