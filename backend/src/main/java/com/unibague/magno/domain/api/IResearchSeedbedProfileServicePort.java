package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;

import java.util.List;

public interface IResearchSeedbedProfileServicePort {
    ResearchSeedbedProfile findById(Long id);
    ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile);
    ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile);
    void deleteById(Long id);
    List<ResearchSeedbedProfile> findAll();
    List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id);
}
