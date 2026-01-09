package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbed;

import java.util.List;

public interface IResearchSeedbedServicePort {
    ResearchSeedbed findById(Long id);
    ResearchSeedbed save(ResearchSeedbed researchSeedbed);
    ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed);
    void deleteById(Long id);
    List<ResearchSeedbed> findAll();
    List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id);
    List<ResearchSeedbed> findResearchSeedbedsWithAssociatedProfiles();
}
