package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.InvestigationGroup;

import java.util.List;

/**
 * Service port interface that defines the contract for investigation group management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to investigation groups,
 * which represent research groups within the institution.
 * </p>
 *
 * @see InvestigationGroup
 */
public interface IInvestigationGroupServicePort {
    
    /**
     * Retrieves an investigation group by its unique identifier.
     *
     * @param id the unique identifier of the investigation group
     * @return the investigation group with the specified ID
     */
    InvestigationGroup findById(Long id);
    
    /**
     * Persists a new investigation group.
     *
     * @param investigationGroup the investigation group to save
     * @return the saved investigation group
     */
    InvestigationGroup save(InvestigationGroup investigationGroup);
    
    /**
     * Updates an existing investigation group.
     *
     * @param id the unique identifier of the investigation group to update
     * @param investigationGroup the investigation group data to update
     * @return the updated investigation group
     */
    InvestigationGroup update(Long id, InvestigationGroup investigationGroup);
    
    /**
     * Deletes an investigation group by its unique identifier.
     *
     * @param id the unique identifier of the investigation group to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all investigation groups in the system.
     *
     * @return a list of all investigation groups
     */
    List<InvestigationGroup> findAll();
    
    /**
     * Retrieves all investigation groups that have associated profiles.
     * <p>
     * This method filters investigation groups to return only those that have
     * been associated with profiles in at least one academic period.
     * </p>
     *
     * @return a list of investigation groups with associated profiles
     */
    List<InvestigationGroup> findInvestigationGroupsWithAssociatedProfiles();
}
