package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbed;

import java.util.List;
import java.util.Optional;

public interface IResearchSeedbedPersistencePort {

    Optional<ResearchSeedbed> findById(Long id);
    ResearchSeedbed save(ResearchSeedbed researchSeedbed);
    ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed);
    void deleteById(Long id);
    List<ResearchSeedbed> findAll();
    List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id);
}
