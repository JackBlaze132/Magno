package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;

import java.util.List;

public interface InvestigationGroupProfileEntityMapper {

    InvestigationGroupProfile toInvestigationGroupProfile(
            InvestigationGroupProfileEntity investigationGroupProfileEntity);

    InvestigationGroupProfileEntity toInvestigationGroupProfileEntity(
            Long id, InvestigationGroupProfile investigationGroupProfile);

    InvestigationGroupProfileEntity toInvestigationGroupProfileEntity(
            InvestigationGroupProfile investigationGroupProfile);

    List<InvestigationGroupProfile> toInvestigationGroupProfileList(
            List<InvestigationGroupProfileEntity> investigationGroupProfileEntities);
}
