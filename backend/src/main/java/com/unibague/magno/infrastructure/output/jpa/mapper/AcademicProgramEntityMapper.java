package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicProgramEntityMapper {

    AcademicProgram toAcademicProgram(AcademicProgramEntity academicProgramEntity);

    @Mapping(source = "id", target = "id")
    AcademicProgramEntity toAcademicProgramEntity(Long id, AcademicProgram academicProgram);

    AcademicProgramEntity toAcademicProgramEntity(AcademicProgram academicProgram);
    List<AcademicProgram> toAcademicProgramList(List<AcademicProgramEntity> academicProgramEntities);
}
