package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.domain.model.AcademicPeriod;

/**
 * Mapper interface for converting academic period request DTOs to domain models.
 */
public interface AcademicPeriodRequestMapper {
    AcademicPeriod toAcademicPeriod(AcademicPeriodRequest academicPeriodRequest);
}
