package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicProgramResponse;
import com.unibague.magno.domain.model.AcademicProgram;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * Mapper interface for converting academic program domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicProgramResponseMapper {
    AcademicProgramResponse toResponse(AcademicProgram academicProgram);
    List<AcademicProgramResponse> toResponseList(List<AcademicProgram> academicPrograms);
    Set<AcademicProgramResponse> toResponseSet(Set<AcademicProgram> academicPrograms);
}
