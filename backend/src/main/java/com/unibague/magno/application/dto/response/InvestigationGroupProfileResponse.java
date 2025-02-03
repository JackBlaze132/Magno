package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestigationGroupProfileResponse {

    private Long id;
    private InvestigationGroupResponse investigationGroup;
    private FunctionaryProfileResponse coordinator;
    private AcademicPeriodResponse academicPeriod;
}
