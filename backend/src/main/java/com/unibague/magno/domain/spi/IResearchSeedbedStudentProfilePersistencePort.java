package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;
import java.util.Optional;

public interface IResearchSeedbedStudentProfilePersistencePort {

    Optional<ResearchSeedbedStudentProfile> findById(Long id);
    ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfile> findAll();
}
