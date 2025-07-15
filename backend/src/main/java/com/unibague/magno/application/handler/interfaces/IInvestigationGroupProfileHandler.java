package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;

import java.util.List;

public interface IInvestigationGroupProfileHandler {
    InvestigationGroupProfileResponse findById(Long id);
    InvestigationGroupProfileResponse save(InvestigationGroupProfileRequest investigationGroupProfileRequest);
    InvestigationGroupProfileResponse updateById(Long id, InvestigationGroupProfileRequest investigationGroupProfileRequest);
    void deleteById(Long id);
    List<InvestigationGroupProfileResponse> findAll();
    List<InvestigationGroupProfileResponse> findAllByAcademicPeriodId(Long id);

    ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId);

    ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualInvestigationGroupReport(Long academicPeriodId1,
                                                                                             Long academicPeriodId2);

    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId);

    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2);
}
