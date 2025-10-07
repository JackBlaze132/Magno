package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FunctionaryProfileResponse {

    private Long id;
    private UserResponse user;
    private AcademicPeriodResponse academicPeriod;
    private DependencyResponse dependency;
    private Long roleId;
}
