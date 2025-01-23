package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicPeriodEntityMapper {
    AcademicPeriod toAcademicPeriod(AcademicPeriodEntity academicPeriodEntity);
    AcademicPeriodEntity toAcademicPeriodEntity(AcademicPeriod academicPeriod);
    List<AcademicPeriod> toAcademicPeriodList(List<AcademicPeriodEntity> academicPeriodEntities);
}
