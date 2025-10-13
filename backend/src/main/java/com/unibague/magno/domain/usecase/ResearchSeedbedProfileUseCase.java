package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileAlreadyExistsInInvestigationGroup;
import com.unibague.magno.domain.exception.researchseedbedprofile.SameCoordinatorAndTutorException;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;
import com.unibague.magno.domain.spi.IResearchSeedbedProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IResearchSeedbedProfileHelper;

import java.util.List;

public class ResearchSeedbedProfileUseCase implements IResearchSeedbedProfileServicePort {

    private final IResearchSeedbedProfilePersistencePort researchSeedbedProfilePersistencePort;
    private final IResearchSeedbedProfileHelper researchSeedbedProfileHelper;

    public ResearchSeedbedProfileUseCase(IResearchSeedbedProfilePersistencePort researchSeedbedPersistencePort,
                                         IResearchSeedbedProfileHelper researchSeedbedProfileHelper) {
        this.researchSeedbedProfilePersistencePort = researchSeedbedPersistencePort;
        this.researchSeedbedProfileHelper = researchSeedbedProfileHelper;
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
        if (researchSeedbedProfile.getCoordinatorId().equals(researchSeedbedProfile.getTutorId())) {
            throw new SameCoordinatorAndTutorException("Coordinator and Tutor cannot be the same person.");
        }
        verifyThatResearchSeedbedProfileDoesNotExist(researchSeedbedProfile);
        ResearchSeedbedProfile rsp = researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(researchSeedbedProfile);
        return researchSeedbedProfilePersistencePort.save(rsp);
    }

    private void verifyThatResearchSeedbedProfileDoesNotExist(ResearchSeedbedProfile researchSeedbedProfile) {
        Long researchSeedbedId = researchSeedbedProfile.getResearchSeedbedId();
        List<ResearchSeedbedProfile> existingProfiles = researchSeedbedProfilePersistencePort
                        .findAllByInvestigationGroupProfileId(researchSeedbedProfile.getInvestigationGroupProfileId());
        boolean exists = existingProfiles.stream()
                .anyMatch(profile -> profile.getResearchSeedbedId().equals(researchSeedbedId));
        if (exists) {
            throw new ResearchSeedbedProfileAlreadyExistsInInvestigationGroup(
                    String.format("A ResearchSeedbedProfile for ResearchSeedbed ID %d already exists", researchSeedbedId)
            );
        }
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

    @Override
    public ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId) {
        return researchSeedbedProfilePersistencePort.getExcelBytesReportById(researchSeedbedProfileId, academicPeriodId);
    }
}
