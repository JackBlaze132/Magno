package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;

import java.util.List;

/**
 * Handler interface for investigation group profile operations.
 * Manages period-specific configurations for investigation groups and provides
 * Excel report generation capabilities for institutional reporting.
 */
public interface IInvestigationGroupProfileHandler {
    InvestigationGroupProfileResponse findById(Long id);
    InvestigationGroupProfileResponse save(InvestigationGroupProfileRequest investigationGroupProfileRequest);
    InvestigationGroupProfileResponse updateById(Long id, InvestigationGroupProfileRequest investigationGroupProfileRequest);
    void deleteById(Long id);
    List<InvestigationGroupProfileResponse> findAll();

    /**
     * Retrieves all investigation group profiles for a specific academic period.
     *
     * @param id the academic period identifier
     * @return list of investigation group profiles in the specified period
     */
    List<InvestigationGroupProfileResponse> findAllByAcademicPeriodId(Long id);

    /**
     * Generates a half-year (semestral) Excel report for investigation groups.
     *
     * @param academicPeriodId the academic period identifier
     * @return Excel report with investigation group data and metadata
     */
    ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId);

    /**
     * Generates an annual Excel report for investigation groups spanning two periods.
     *
     * @param academicPeriodId1 the first academic period identifier
     * @param academicPeriodId2 the second academic period identifier
     * @return Excel report with investigation group data and metadata
     */
    ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualInvestigationGroupReport(Long academicPeriodId1,
                                                                                             Long academicPeriodId2);

    /**
     * Generates a half-year (semestral) Excel report for active research seedbeds.
     *
     * @param academicPeriodId the academic period identifier
     * @return Excel report with active seedbeds data and metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId);

    /**
     * Generates an annual Excel report for active research seedbeds spanning two periods.
     *
     * @param academicPeriodId1 the first academic period identifier
     * @param academicPeriodId2 the second academic period identifier
     * @return Excel report with active seedbeds data and metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2);
}
