package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;

public interface IResearchSeedbedStudentProfileServicePort {
    ResearchSeedbedStudentProfile findById(Long id);
    ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfile> findAll();
}
