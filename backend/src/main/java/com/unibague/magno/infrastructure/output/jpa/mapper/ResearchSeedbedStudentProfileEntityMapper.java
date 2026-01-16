package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;

import java.util.List;

/**
 * Mapper interface for converting between {@link ResearchSeedbedStudentProfile} domain model and {@link ResearchSeedbedStudentProfileEntity} JPA entity.
 */
public interface ResearchSeedbedStudentProfileEntityMapper {

    ResearchSeedbedStudentProfile toResearchSeedbedStudentProfile(
            ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity);

    ResearchSeedbedStudentProfileEntity toResearchSeedbedStudentProfileEntity(
            Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);

    ResearchSeedbedStudentProfileEntity toResearchSeedbedStudentProfileEntity(
            ResearchSeedbedStudentProfile researchSeedbedStudentProfile);

    List<ResearchSeedbedStudentProfile> toResearchSeedbedStudentProfileList(
            List<ResearchSeedbedStudentProfileEntity> researchSeedbedStudentProfileEntities);
}
