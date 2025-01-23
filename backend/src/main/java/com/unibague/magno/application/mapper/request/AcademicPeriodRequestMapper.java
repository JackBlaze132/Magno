package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.domain.model.AcademicPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicPeriodRequestMapper {
    AcademicPeriod toAcademicPeriod(AcademicPeriodRequest academicPeriodRequest);
}
