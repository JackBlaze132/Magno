package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link ResearchSeedbed} domain model and {@link ResearchSeedbedEntity} JPA entity.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedEntityMapper {

    ResearchSeedbed toResearchSeedbed(ResearchSeedbedEntity researchSeedbedEntity);

    @Mapping(source = "id", target = "id")
    ResearchSeedbedEntity toResearchSeedbedEntity(Long id, ResearchSeedbed researchSeedbed);

    ResearchSeedbedEntity toResearchSeedbedEntity(ResearchSeedbed researchSeedbed);
    List<ResearchSeedbed> toResearchSeedbedList(List<ResearchSeedbedEntity> researchSeedbedEntities);
}
