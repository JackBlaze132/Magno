package com.unibague.magno.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionaryProfileResponse {

    private UserResponse userResponse;
    private AcademicPeriodResponse academicPeriodResponse;
    private DependencyResponse dependencyResponse;
}
