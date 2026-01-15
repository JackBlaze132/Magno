package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.InvestigationGroup;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing investigation group data.
 * <p>
 * This interface defines the contract for persisting and retrieving investigation groups.
 * Investigation groups are organizational units that contain research seedbeds and
 * are associated with profiles (coordinators, researchers, etc.).
 * </p>
 */
public interface IInvestigationGroupPersistencePort {
    Optional<InvestigationGroup> findById(Long id);
    InvestigationGroup save(InvestigationGroup investigationGroup);
    InvestigationGroup update(Long id, InvestigationGroup investigationGroup);
    void deleteById(Long id);
    List<InvestigationGroup> findAll();

    /**
     * Retrieves all investigation groups that have at least one associated profile.
     * <p>
     * This method filters out investigation groups without any profiles,
     * useful for displaying only active groups with assigned personnel.
     * </p>
     *
     * @return a list of investigation groups that have associated profiles
     */
    List<InvestigationGroup> findInvestigationGroupsWithAssociatedProfiles();
}
