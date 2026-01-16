package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;

import java.util.List;

/**
 * Mapper interface for converting between {@link AcademicProgram} domain model and {@link AcademicProgramEntity} JPA entity.
 */
public interface AcademicProgramEntityMapper {

    AcademicProgram toAcademicProgram(AcademicProgramEntity academicProgramEntity);
    AcademicProgramEntity toAcademicProgramEntity(Long id, AcademicProgram academicProgram);
    AcademicProgramEntity toAcademicProgramEntity(AcademicProgram academicProgram);
    List<AcademicProgram> toAcademicProgramList(List<AcademicProgramEntity> academicProgramEntities);
    AcademicProgramEntity toAcademicProgramEntity(IntegraAcademicProgram academicProgram);
}
