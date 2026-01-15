package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbed;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing research seedbed data.
 * <p>
 * This interface defines the contract for persisting and retrieving research seedbeds.
 * Research seedbeds are groups within investigation groups where students participate
 * in research activities under the guidance of tutors and coordinators.
 * </p>
 */
public interface IResearchSeedbedPersistencePort {

    Optional<ResearchSeedbed> findById(Long id);
    ResearchSeedbed save(ResearchSeedbed researchSeedbed);
    ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed);
    void deleteById(Long id);
    List<ResearchSeedbed> findAll();

    /**
     * Retrieves all research seedbeds where a specific user participates.
     *
     * @param id the unique identifier of the user
     * @return a list of research seedbeds associated with the user
     */
    List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id);

    /**
     * Retrieves all research seedbeds that have at least one associated profile.
     * <p>
     * This method filters out seedbeds without any profiles, useful for
     * displaying only active seedbeds with assigned personnel.
     * </p>
     *
     * @return a list of research seedbeds that have associated profiles
     */
    List<ResearchSeedbed> findResearchSeedbedsWithAssociatedProfiles();
}
