package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileAlreadyExistsInAcademicPeriod;
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
                        String.format("Perfil de semillero de investigación con ID %d no encontrado", id)
                ));
    }

    @Override
    public ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile) {
        verifyThatResearchSeedbedProfileDoesNotExist(researchSeedbedProfile);
        ResearchSeedbedProfile rsp = verificationsBeforeSaveOrUpdate(researchSeedbedProfile);
        return researchSeedbedProfilePersistencePort.save(rsp);
    }

    private ResearchSeedbedProfile verificationsBeforeSaveOrUpdate(ResearchSeedbedProfile researchSeedbedProfile) {
        if (researchSeedbedProfile.getCoordinatorId().equals(researchSeedbedProfile.getTutorId())) {
            throw new SameCoordinatorAndTutorException("El coordinador y el tutor no pueden ser la misma persona.");
        }
        researchSeedbedProfileHelper.verifyAcademicPeriodIsCurrent(
                researchSeedbedProfile.getAcademicPeriodId(),
                "El período académico debe estar activo para crear o actualizar un perfil de semillero de investigación."
        );
        return researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(researchSeedbedProfile);
    }

    private void verifyThatResearchSeedbedProfileDoesNotExist(ResearchSeedbedProfile researchSeedbedProfile) {
        Long researchSeedbedId = researchSeedbedProfile.getResearchSeedbedId();
        List<ResearchSeedbedProfile> existingProfiles = researchSeedbedProfilePersistencePort
                        .findAllByAcademicPeriodId(researchSeedbedProfile.getAcademicPeriodId());
        boolean exists = existingProfiles.stream()
                .anyMatch(profile -> profile.getResearchSeedbedId().equals(researchSeedbedId));
        if (exists) {
            throw new ResearchSeedbedProfileAlreadyExistsInAcademicPeriod(
                    String.format("Este semillero ya existe en el periodo academico actual")
            );
        }
    }

    @Override
    public ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile) {
        researchSeedbedProfile.setId(id);
        
        // Get the existing profile to capture the OLD coordinator ID before updating
        ResearchSeedbedProfile existingProfile = findById(id);
        Long oldCoordinatorId = existingProfile.getCoordinatorId();
        Long academicPeriodId = researchSeedbedProfile.getAcademicPeriodId();

        ResearchSeedbedProfile rsp = verificationsBeforeSaveOrUpdate(researchSeedbedProfile);
        
        // Update the profile with the new coordinator
        ResearchSeedbedProfile response = researchSeedbedProfilePersistencePort.update(id, rsp);
        
        // Get the updated list of seedbed profiles AFTER the update
        List<ResearchSeedbedProfile> researchSeedbedProfiles = findAllByAcademicPeriodId(academicPeriodId);
        
        // Handle functionary profile changes only if coordinator changed
        Long newCoordinatorId = response.getCoordinatorId();
        if (!oldCoordinatorId.equals(newCoordinatorId)) {
            researchSeedbedProfileHelper.handleFunctionaryProfileChangesOnCoordinatorUpdate(
                    researchSeedbedProfiles,
                    academicPeriodId,
                    oldCoordinatorId
            );
        }
        
        return response;
    }

    @Override
    public void deleteById(Long id) {
        if (researchSeedbedProfilePersistencePort.findById(id).isEmpty()) {
            throw new ResearchSeedbedNotFoundException(
                    String.format("No se pudo eliminar el perfil de semillero de investigación con ID %d porque no existe", id)
            );
        }
        ResearchSeedbedProfile rsp = findById(id);
        researchSeedbedProfileHelper.verifyAcademicPeriodIsCurrent(
                rsp.getAcademicPeriodId(),
                "El período académico debe estar activo para eliminar un perfil de semillero de investigación."
        );

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
    public List<ResearchSeedbedProfile> findAllByAcademicPeriodId(Long academicPeriodId) {
        return researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
    }

    @Override
    public ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId) {
        return researchSeedbedProfilePersistencePort.getExcelBytesReportById(researchSeedbedProfileId, academicPeriodId);
    }
}
