package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.AcademicProgramRequest;
import com.unibague.magno.domain.model.AcademicProgram;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting academic program request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicProgramRequestMapper {
    AcademicProgram toAcademicProgram(AcademicProgramRequest academicProgramRequest);
}
