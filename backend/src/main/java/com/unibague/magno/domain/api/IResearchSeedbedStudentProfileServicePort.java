package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;
import java.util.Map;

public interface IResearchSeedbedStudentProfileServicePort {
    ResearchSeedbedStudentProfile findById(Long id);
    ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    void deleteById(Long id);
    List<ResearchSeedbedStudentProfile> findAll();
    List<ResearchSeedbedStudentProfile> saveAllByExcel(Long researchSeedbedProfileId,
                                                       List<Map<String, String>> researchSeedbedStudentProfiles);
    boolean existsByStudentProfileIdAndResearchSeedbedProfileId(Long studentProfileId, Long researchSeedbedProfileId);

    List<ResearchSeedbedStudentProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
