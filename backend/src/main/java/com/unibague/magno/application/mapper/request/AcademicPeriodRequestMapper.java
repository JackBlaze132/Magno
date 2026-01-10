package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.domain.model.AcademicPeriod;

public interface AcademicPeriodRequestMapper {
    AcademicPeriod toAcademicPeriod(AcademicPeriodRequest academicPeriodRequest);
}
