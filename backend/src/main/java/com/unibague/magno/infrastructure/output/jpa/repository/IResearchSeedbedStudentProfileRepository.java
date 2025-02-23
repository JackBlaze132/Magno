package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResearchSeedbedStudentProfileRepository extends JpaRepository<ResearchSeedbedStudentProfileEntity, Long> {
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);
}
