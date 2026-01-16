package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;

import java.util.List;

/**
 * Mapper interface for converting between {@link ResearchSeedbedProfile} domain model and {@link ResearchSeedbedProfileEntity} JPA entity.
 */
public interface ResearchSeedbedProfileEntityMapper {

    ResearchSeedbedProfile toResearchSeedbedProfile(
            ResearchSeedbedProfileEntity researchSeedbedProfileEntity);

    ResearchSeedbedProfileEntity toResearchSeedbedProfileEntity(
            Long id, ResearchSeedbedProfile researchSeedbedProfile);

    ResearchSeedbedProfileEntity toResearchSeedbedProfileEntity(
            ResearchSeedbedProfile researchSeedbedProfile);

    List<ResearchSeedbedProfile> toResearchSeedbedProfileList(
            List<ResearchSeedbedProfileEntity> researchSeedbedProfileEntities);
}
