package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedAlreadyExistsException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedHasAssociatedProfilesException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.spi.IResearchSeedbedPersistencePort;

import java.util.List;

public class ResearchSeedbedUseCase implements IResearchSeedbedServicePort {

    private final IResearchSeedbedPersistencePort researchSeedbedPersistencePort;

    public ResearchSeedbedUseCase(IResearchSeedbedPersistencePort researchSeedbedPersistencePort) {
        this.researchSeedbedPersistencePort = researchSeedbedPersistencePort;
    }

    @Override
    public ResearchSeedbed findById(Long id) {
        return researchSeedbedPersistencePort
                .findById(id)
                .orElseThrow(() -> new ResearchSeedbedNotFoundException(
                        String.format("Semillero de investigación con ID %d no encontrado", id)
                ));
    }

    @Override
    public ResearchSeedbed save(ResearchSeedbed researchSeedbed) {
        verifyThatResearchSeedbedDoesNotExist(researchSeedbed);
        return researchSeedbedPersistencePort.save(researchSeedbed);
    }

    /**
     * Verifies that a research seedbed with the same name doesn't already exist.
     * Uses trim() to ignore leading/trailing whitespaces and case-insensitive comparison.
     *
     * @param researchSeedbed The research seedbed to verify
     * @throws ResearchSeedbedAlreadyExistsException if a seedbed with the same name exists
     */
    private void verifyThatResearchSeedbedDoesNotExist(ResearchSeedbed researchSeedbed) {
        String normalizedName = researchSeedbed.getName().trim().toLowerCase();
        
        List<ResearchSeedbed> existingSeedbeds = researchSeedbedPersistencePort.findAll();
        boolean exists = existingSeedbeds.stream()
                .anyMatch(seedbed -> seedbed.getName().trim().toLowerCase().equals(normalizedName));
        
        if (exists) {
            throw new ResearchSeedbedAlreadyExistsException(
                    String.format("Ya existe un semillero de investigación con el nombre '%s'", 
                            researchSeedbed.getName().trim())
            );
        }
    }

    @Override
    public ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("No se pudo actualizar el semillero de investigación con ID %d porque no existe", id)
            );
        }
        verifyThatResearchSeedbedDoesNotExist(researchSeedbed);
        return researchSeedbedPersistencePort.update(id, researchSeedbed);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("No se pudo eliminar el semillero de investigación con ID %d porque no existe", id)
            );
        }
        ResearchSeedbed researchSeedbed = findById(id);
        List<ResearchSeedbed> researchSeedbedsWithProfiles =
                researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles();
        boolean hasAssociatedProfiles = researchSeedbedsWithProfiles.stream()
                .anyMatch(seedbed -> seedbed.getId().equals(id));

        if (hasAssociatedProfiles) {
            throw new ResearchSeedbedHasAssociatedProfilesException
                    ("No se pudo eliminar el semillero de investigación "
                            + researchSeedbed.getName() + " porque tiene perfiles asociados");
        }
        researchSeedbedPersistencePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbed> findAll() {
        return researchSeedbedPersistencePort.findAll();
    }

    @Override
    public List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id) {
        return researchSeedbedPersistencePort.findResearchSeedbedsByUserId(id);
    }

    @Override
    public List<ResearchSeedbed> findResearchSeedbedsWithAssociatedProfiles() {
        return researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles();
    }

}
