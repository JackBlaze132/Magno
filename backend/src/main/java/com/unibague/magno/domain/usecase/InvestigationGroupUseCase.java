package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupAlreadyExistsException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupHasAssociatedProfilesException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.domain.spi.IInvestigationGroupPersistencePort;

import java.util.List;

/**
 * Use case implementation for managing investigation groups.
 * <p>
 * Handles business logic for investigation group operations. Investigation groups
 * are organizational units that contain research seedbeds and are associated with
 * coordinators and researchers.
 * </p>
 * <p>
 * Business rules enforced:
 * <ul>
 *   <li>Group names must be unique (case-insensitive)</li>
 *   <li>Groups with associated profiles cannot be deleted</li>
 * </ul>
 * </p>
 */
public class InvestigationGroupUseCase implements IInvestigationGroupServicePort {

    private final IInvestigationGroupPersistencePort investigationGroupPersistencePort;

    public InvestigationGroupUseCase(IInvestigationGroupPersistencePort investigationGroupPersistencePort) {
        this.investigationGroupPersistencePort = investigationGroupPersistencePort;
    }

    @Override
    public InvestigationGroup findById(Long id) {
        return investigationGroupPersistencePort.findById(id)
                .orElseThrow(() -> new InvestigationGroupNotFoundException(
                        String.format("InvestigationGroup with ID %d not found", id)));
    }

    @Override
    public InvestigationGroup save(InvestigationGroup investigationGroup) {
        verifyThatInvestigationGroupDoesNotExist(investigationGroup, null);
        return investigationGroupPersistencePort.save(investigationGroup);
    }

    /**
     * Verifies that an investigation group with the same name doesn't already exist.
     * Uses case-insensitive comparison and trims whitespace.
     *
     * @param investigationGroup the investigation group to verify
     * @param excludeId optional ID to exclude from the check (used when updating)
     * @throws InvestigationGroupAlreadyExistsException if a group with the same name exists
     */
    private void verifyThatInvestigationGroupDoesNotExist(InvestigationGroup investigationGroup, Long excludeId) {
        String normalizedName = investigationGroup.getName().trim().toLowerCase();
        
        List<InvestigationGroup> existingGroups = investigationGroupPersistencePort.findAll();
        boolean exists = existingGroups.stream()
                .filter(group -> excludeId == null || !group.getId().equals(excludeId))
                .anyMatch(group -> group.getName().trim().toLowerCase().equals(normalizedName));
        
        if (exists) {
            throw new InvestigationGroupAlreadyExistsException(
                    String.format("Ya existe un grupo de investigación con el nombre '%s'", 
                            investigationGroup.getName().trim())
            );
        }
    }

    @Override
    public InvestigationGroup update(Long id, InvestigationGroup investigationGroup) {
        if(investigationGroupPersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupNotFoundException(
                    String.format("InvestigationGroup with ID %d could not be updated because it does not exist", id));
        }
        verifyThatInvestigationGroupDoesNotExist(investigationGroup, id);
        return investigationGroupPersistencePort.update(id, investigationGroup);
    }

    @Override
    public void deleteById(Long id) {
        if(investigationGroupPersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupNotFoundException(
                    String.format("InvestigationGroup with ID %d could not be deleted because it does not exist", id));
        }

        InvestigationGroup investigationGroup = findById(id);
        List<InvestigationGroup> associatedGroups =
                investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles();

        boolean hasAssociatedProfiles = associatedGroups.stream()
                .anyMatch(group -> group.getId().equals(investigationGroup.getId()));

        if (hasAssociatedProfiles) {
            throw new InvestigationGroupHasAssociatedProfilesException(
                    "El grupo de investigacion " + investigationGroup.getName()
                           + " no puede ser eliminado porque tiene perfiles asociados");
        }
        investigationGroupPersistencePort.deleteById(id);
    }

    @Override
    public List<InvestigationGroup> findAll() {
        return investigationGroupPersistencePort.findAll();
    }

    @Override
    public List<InvestigationGroup> findInvestigationGroupsWithAssociatedProfiles() {
        return investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles();
    }
}
