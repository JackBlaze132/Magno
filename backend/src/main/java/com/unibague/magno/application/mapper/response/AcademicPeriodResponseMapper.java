package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.domain.model.AcademicPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting academic period domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicPeriodResponseMapper {
    AcademicPeriodResponse toResponse(AcademicPeriod academicPeriod);
    List<AcademicPeriodResponse> toResponseList(List<AcademicPeriod> academicPeriods);
}
