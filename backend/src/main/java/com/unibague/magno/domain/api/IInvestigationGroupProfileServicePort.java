package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;

import java.util.List;

public interface IInvestigationGroupProfileServicePort {
    InvestigationGroupProfile findById(Long id);
    InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile);
    InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile);
    void deleteById(Long id);
    List<InvestigationGroupProfile> findAll();
    List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId);
    ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualYearInvestigationGroupReport(Long academicPeriodId1, Long academicPeriodId2);

    ExcelReport<ActiveSeedbedsHYRMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId);
}
