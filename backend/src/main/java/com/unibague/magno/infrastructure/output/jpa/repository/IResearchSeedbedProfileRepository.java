package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IResearchSeedbedProfileRepository extends JpaRepository<ResearchSeedbedProfileEntity, Long> {
    List<ResearchSeedbedProfileEntity> findAllByInvestigationGroupProfileId(Long id);
}
