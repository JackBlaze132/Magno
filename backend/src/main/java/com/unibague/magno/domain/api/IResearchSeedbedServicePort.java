package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbed;

import java.util.List;

/**
 * Service port interface that defines the contract for research seedbed management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to research seedbeds,
 * which represent student-led research groups within investigation groups.
 * </p>
 *
 * @see ResearchSeedbed
 */
public interface IResearchSeedbedServicePort {
    
    /**
     * Retrieves a research seedbed by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed
     * @return the research seedbed with the specified ID
     */
    ResearchSeedbed findById(Long id);
    
    /**
     * Persists a new research seedbed.
     *
     * @param researchSeedbed the research seedbed to save
     * @return the saved research seedbed
     */
    ResearchSeedbed save(ResearchSeedbed researchSeedbed);
    
    /**
     * Updates an existing research seedbed.
     *
     * @param id the unique identifier of the research seedbed to update
     * @param researchSeedbed the research seedbed data to update
     * @return the updated research seedbed
     */
    ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed);
    
    /**
     * Deletes a research seedbed by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all research seedbeds in the system.
     *
     * @return a list of all research seedbeds
     */
    List<ResearchSeedbed> findAll();
    
    /**
     * Retrieves all research seedbeds associated with a specific user.
     *
     * @param id the unique identifier of the user
     * @return a list of research seedbeds for the specified user
     */
    List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id);
    
    /**
     * Retrieves all research seedbeds that have associated profiles.
     * <p>
     * This method filters research seedbeds to return only those that have
     * been associated with profiles in at least one academic period.
     * </p>
     *
     * @return a list of research seedbeds with associated profiles
     */
    List<ResearchSeedbed> findResearchSeedbedsWithAssociatedProfiles();
}
