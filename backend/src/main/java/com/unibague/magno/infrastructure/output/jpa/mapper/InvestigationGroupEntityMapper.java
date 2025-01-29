package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigationGroupEntityMapper {

    InvestigationGroup toInvestigationGroup(InvestigationGroupEntity investigationGroupEntity);

    @Mapping(source = "id", target = "id")
    InvestigationGroupEntity toInvestigationGroupEntity(Long id, InvestigationGroup investigationGroup);

    InvestigationGroupEntity toInvestigationGroupEntity(InvestigationGroup investigationGroup);
    List<InvestigationGroup> toInvestigationGroupList(List<InvestigationGroupEntity> investigationGroupEntities);
}
