package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IInvestigationGroupProfileServicePort;
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

    public InvestigationGroupProfileUseCase
            (IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort,
             IUserServicePort userServicePort,
             IFunctionaryProfileServicePort functionaryProfileServicePort,
             IInvestigationGroupProfileHelper investigationGroupProfileHelper) {
        this.investigationGroupProfilePersistencePort = investigationGroupProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.functionaryProfileServicePort = functionaryProfileServicePort;
        this.investigationGroupProfileHelper = investigationGroupProfileHelper;
    }

    @Override
    public InvestigationGroupProfile findById(Long id) {
        return investigationGroupProfilePersistencePort.findById(id)
                .orElseThrow(() -> new InvestigationGroupProfileNotFoundException(
                        String.format("InvestigationGroupProfile with ID %d not found", id)
                ));
    }

    @Override
    public InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile) {
        verifyThatInvestigationGroupProfileDoesNotExist(investigationGroupProfile.getAcademicPeriodId(),
                investigationGroupProfile.getInvestigationGroupId());
        InvestigationGroupProfile igp =
                investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(investigationGroupProfile);
        return investigationGroupProfilePersistencePort.save(igp);
    }

    private void verifyThatInvestigationGroupProfileDoesNotExist(Long academicPeriodId, Long investigationGroupId) {
        List<InvestigationGroupProfile> existingProfiles =
                investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
        boolean exists = existingProfiles.stream()
                .anyMatch(profile -> profile.getInvestigationGroupId().equals(investigationGroupId));
        if (exists) {
            throw new InvestigationGroupProfileDuplicatedInSameAcademicPeriodException(
                    String.format("An InvestigationGroupProfile for InvestigationGroup ID %d in AcademicPeriod ID %d already exists",
                            investigationGroupId, academicPeriodId)
            );
        }

    }

    @Override
    public InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile) {
        if(investigationGroupProfilePersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupProfileNotFoundException(
                    String.format("InvestigationGroupProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        InvestigationGroupProfile igp =
                investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(investigationGroupProfile);
        return investigationGroupProfilePersistencePort.update(id, igp);
    }

    @Override
    public void deleteById(Long id) {
        if (investigationGroupProfilePersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupProfileNotFoundException(
                    String.format("InvestigationGroupProfile with ID %d could not be deleted because it does not exist", id)
            );
        }
        investigationGroupProfilePersistencePort.deleteById(id);
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
