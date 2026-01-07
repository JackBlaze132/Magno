package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IInvestigationGroupProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileDuplicatedInSameAcademicPeriodException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IInvestigationGroupProfileHelper;

import java.util.List;

public class InvestigationGroupProfileUseCase implements IInvestigationGroupProfileServicePort {

    private final IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final IInvestigationGroupProfileHelper investigationGroupProfileHelper;
    private final IRoleServicePort roleServicePort;

    public InvestigationGroupProfileUseCase
            (IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort,
             IUserServicePort userServicePort,
             IFunctionaryProfileServicePort functionaryProfileServicePort,
             IInvestigationGroupProfileHelper investigationGroupProfileHelper,
             IRoleServicePort roleServicePort) {
        this.investigationGroupProfilePersistencePort = investigationGroupProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.functionaryProfileServicePort = functionaryProfileServicePort;
        this.investigationGroupProfileHelper = investigationGroupProfileHelper;
        this.roleServicePort = roleServicePort;
    }

    @Override
    public InvestigationGroupProfile findById(Long id) {
        return investigationGroupProfilePersistencePort.findById(id)
                .orElseThrow(() -> new InvestigationGroupProfileNotFoundException(
                        String.format("Perfil de grupo de investigación con ID %d no encontrado", id)
                ));
    }

    @Override
    public InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile) {

        Long academicPeriodId = investigationGroupProfile.getAcademicPeriodId();

        investigationGroupProfileHelper.verifyAcademicPeriodIsCurrent(
                academicPeriodId,
                "El período académico debe estar activo para crear un nuevo perfil de grupo de investigación"
        );

        verifyThatInvestigationGroupProfileDoesNotExist(
                academicPeriodId, investigationGroupProfile.getInvestigationGroupId()
        );

        investigationGroupProfileHelper.verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(
                investigationGroupProfile.getCoordinatorId(),
                academicPeriodId
        );

        InvestigationGroupProfile igp = investigationGroupProfileHelper
                .verifyUserHasFunctionaryProfile(investigationGroupProfile);

        return investigationGroupProfilePersistencePort.save(igp);
    }

    private void verifyThatInvestigationGroupProfileDoesNotExist(Long academicPeriodId, Long investigationGroupId) {
        List<InvestigationGroupProfile> existingProfiles =
                investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
        boolean exists = existingProfiles.stream()
                .anyMatch(profile -> profile.getInvestigationGroupId().equals(investigationGroupId));
        if (exists) {
            throw new InvestigationGroupProfileDuplicatedInSameAcademicPeriodException(
                    String.format("Ya existe un perfil de grupo de investigación con ID %d para el período académico con ID %d",
                            investigationGroupId, academicPeriodId)
            );
        }

    }

    @Override
    public InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile) {
        InvestigationGroupProfile existingIgp = findById(id);
        
        investigationGroupProfileHelper.verifyAcademicPeriodIsCurrent(
                investigationGroupProfile.getAcademicPeriodId(),
                "El período académico debe estar activo para actualizar un perfil de grupo de investigación"
        );
        
        // Store the old coordinator ID before updating
        Long oldCoordinatorId = existingIgp.getCoordinatorId();
        Long academicPeriodId = existingIgp.getAcademicPeriodId();
        
        InvestigationGroupProfile igp =
                investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(investigationGroupProfile);
        
        InvestigationGroupProfile updatedIgp = investigationGroupProfilePersistencePort.update(id, igp);
        
        // Handle functionary profile changes if coordinator changed
        Long newCoordinatorId = updatedIgp.getCoordinatorId();
        if (!oldCoordinatorId.equals(newCoordinatorId)) {
            investigationGroupProfileHelper.handleFunctionaryProfileChangeOnUpdate(
                    oldCoordinatorId, academicPeriodId, id
            );
        }
        
        return updatedIgp;
    }

    @Override
    public void deleteById(Long id) {
        if (investigationGroupProfilePersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupProfileNotFoundException(
                    String.format("No se pudo eliminar el perfil de grupo de investigación con ID %d porque no existe", id)
            );
        }
        InvestigationGroupProfile igp = findById(id);
        Long coordinatorId = igp.getCoordinatorId();
        Long academicPeriodId = igp.getAcademicPeriodId();
        verifyAcademicPeriodIsCurrentStatusBeforeDelete(igp);
        investigationGroupProfilePersistencePort.deleteById(id);
        handleFunctionaryProfileChangeOnDelete(coordinatorId, academicPeriodId);
    }

    /**
     * Deletes or updates the functionary profile of the coordinator of the investigation group profile
     * after deletion of the investigation group profile.
     * @param coordinatorId ID of the coordinator (FunctionaryProfile)
     * @param academicPeriodId ID of the academic period
     */
    private void handleFunctionaryProfileChangeOnDelete
            (Long coordinatorId, Long academicPeriodId) {
        List<InvestigationGroupProfile> investigationGroupProfiles =
                investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
        investigationGroupProfileHelper.handleFunctionaryProfileChangeOnDelete(investigationGroupProfiles, coordinatorId);
    }

    private void verifyAcademicPeriodIsCurrentStatusBeforeDelete(InvestigationGroupProfile igp) {
        investigationGroupProfileHelper.verifyAcademicPeriodIsCurrent
                (igp.getAcademicPeriodId(), "El período académico debe estar activo para eliminar un perfil de grupo de investigación");
    }

    @Override
    public List<InvestigationGroupProfile> findAll() {
        return investigationGroupProfilePersistencePort.findAll();
    }

    @Override
    public List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId) {
        return investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
    }

    @Override
    public ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId) {
        return investigationGroupProfilePersistencePort.getExcelBytesForHalfYearInvestigationGroupReport(academicPeriodId);
    }

    @Override
    public ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualYearInvestigationGroupReport(Long academicPeriodId1, Long academicPeriodId2) {
        return investigationGroupProfilePersistencePort.getExcelBytesForAnnualYearInvestigationGroupReport(academicPeriodId1, academicPeriodId2);
    }

    @Override
    public ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId) {
        return investigationGroupProfilePersistencePort.getExcelBytesForHalfYearActiveSeedbedsReport(academicPeriodId);
    }

    @Override
    public ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2) {
        return investigationGroupProfilePersistencePort.getExcelBytesForAnnualActiveSeedbedsReport(academicPeriodId1, academicPeriodId2);
    }
}
