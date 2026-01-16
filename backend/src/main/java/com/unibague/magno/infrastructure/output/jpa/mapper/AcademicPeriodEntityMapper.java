package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link AcademicPeriod} domain model and {@link AcademicPeriodEntity} JPA entity.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicPeriodEntityMapper {

    AcademicPeriod toAcademicPeriod(AcademicPeriodEntity academicPeriodEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "academicPeriod.current", target = "isCurrent")
    @Mapping(source = "academicPeriod.visible", target = "isVisible")
    AcademicPeriodEntity toAcademicPeriodEntity(Long id, AcademicPeriod academicPeriod);

    @Mapping(source = "current", target = "isCurrent")
    @Mapping(source = "visible", target = "isVisible")
    AcademicPeriodEntity toAcademicPeriodEntity(AcademicPeriod academicPeriod);
    List<AcademicPeriod> toAcademicPeriodList(List<AcademicPeriodEntity> academicPeriodEntities);
}
