package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.spi.IResearchSeedbedProfilePersistencePort;

import java.util.List;

public class ResearchSeedbedProfileUseCase implements IResearchSeedbedProfileServicePort {

    private final IResearchSeedbedProfilePersistencePort researchSeedbedProfilePersistencePort;

    public ResearchSeedbedProfileUseCase(IResearchSeedbedProfilePersistencePort researchSeedbedPersistencePort) {
        this.researchSeedbedProfilePersistencePort = researchSeedbedPersistencePort;
    }

    @Override
    public ResearchSeedbedProfile findById(Long id) {
        return researchSeedbedProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ResearchSeedbedNotFoundException(
                        String.format("ResearchSeedbedProfile with ID %d not found", id)
                ));
    }

    @Override
    public ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile) {
        return researchSeedbedProfilePersistencePort.save(researchSeedbedProfile);
    }

    @Override
    public ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile) {
        if(researchSeedbedProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("ResearchSeedbedProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        return researchSeedbedProfilePersistencePort.update(id, researchSeedbedProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("ResearchSeedbedProfile with ID %d could not be deleted because it does not exist", id)
            );
        }
        researchSeedbedProfilePersistencePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedProfile> findAll() {
        return researchSeedbedProfilePersistencePort.findAll();
    }

    @Override
    public List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id) {
        return researchSeedbedProfilePersistencePort.findAllByInvestigationGroupProfileId(id);
    }
}
