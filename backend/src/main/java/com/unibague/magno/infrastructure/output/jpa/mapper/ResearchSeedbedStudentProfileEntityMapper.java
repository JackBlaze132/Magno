package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;

import java.util.List;

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
