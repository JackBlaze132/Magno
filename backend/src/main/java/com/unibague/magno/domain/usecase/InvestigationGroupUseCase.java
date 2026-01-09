package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupHasAssociatedProfilesException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.domain.spi.IInvestigationGroupPersistencePort;

import java.util.List;

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
        return investigationGroupPersistencePort.save(investigationGroup);
    }

    @Override
    public InvestigationGroup update(Long id, InvestigationGroup investigationGroup) {
        if(investigationGroupPersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupNotFoundException(
                    String.format("InvestigationGroup with ID %d could not be updated because it does not exist", id));
        }
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
