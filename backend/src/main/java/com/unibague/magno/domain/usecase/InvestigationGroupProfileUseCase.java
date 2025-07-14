package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IInvestigationGroupProfileServicePort;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;

import java.util.List;

public class InvestigationGroupProfileUseCase implements IInvestigationGroupProfileServicePort {

    private final IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort;

    public InvestigationGroupProfileUseCase
            (IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort) {
        this.investigationGroupProfilePersistencePort = investigationGroupProfilePersistencePort;
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
        return investigationGroupProfilePersistencePort.save(investigationGroupProfile);
    }

    @Override
    public InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile) {
        if(investigationGroupProfilePersistencePort.findById(id).isEmpty()) {
            throw new InvestigationGroupProfileNotFoundException(
                    String.format("InvestigationGroupProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        return investigationGroupProfilePersistencePort.update(id, investigationGroupProfile);
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
    public ExcelReport<ActiveSeedbedsHYRMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId) {
        return investigationGroupProfilePersistencePort.getExcelBytesForHalfYearActiveSeedbedsReport(academicPeriodId);
    }
}
