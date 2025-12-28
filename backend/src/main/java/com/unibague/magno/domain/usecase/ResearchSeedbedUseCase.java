package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
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
        return researchSeedbedPersistencePort.save(researchSeedbed);
    }

    @Override
    public ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("No se pudo actualizar el semillero de investigación con ID %d porque no existe", id)
            );
        }
        return researchSeedbedPersistencePort.update(id, researchSeedbed);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedPersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("No se pudo eliminar el semillero de investigación con ID %d porque no existe", id)
            );
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
}
