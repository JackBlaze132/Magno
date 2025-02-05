package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.exception.ResearchSeedbedNotFoundException;
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
                        String.format("ResearchSeedbed with ID %d not found", id)
                ));
    }

    @Override
    public ResearchSeedbed save(ResearchSeedbed researchSeedbed) {
        return researchSeedbedPersistencePort.save(researchSeedbed);
    }

    @Override
    public ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("ResearchSeedbed with ID %d could not be updated because it does not exist", id)
            );
        }
        return researchSeedbedPersistencePort.update(id, researchSeedbed);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("ResearchSeedbed with ID %d could not be deleted because it does not exist", id)
            );
        }
        researchSeedbedPersistencePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbed> findAll() {
        return researchSeedbedPersistencePort.findAll();
    }
}
