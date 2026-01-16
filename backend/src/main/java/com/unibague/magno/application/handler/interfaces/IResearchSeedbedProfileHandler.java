package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;

import java.util.List;

/**
 * Handler interface for research seedbed profile operations.
 * Manages period-specific configurations for research seedbeds including
 * coordinator and tutor assignments, and provides Excel report generation.
 */
public interface IResearchSeedbedProfileHandler {
    ResearchSeedbedProfileResponse findById(Long id);
    ResearchSeedbedProfileResponse save(ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
    ResearchSeedbedProfileResponse updateById(Long id, ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
    void deleteById(Long id);
    List<ResearchSeedbedProfileResponse> findAll();

    /**
     * Retrieves all research seedbed profiles under a specific investigation group profile.
     *
     * @param id the investigation group profile identifier
     * @return list of research seedbed profiles in the specified group
     */
    List<ResearchSeedbedProfileResponse> findAllByInvestigationGroupProfileId(Long id);

    /**
     * Generates an Excel report for a specific research seedbed profile.
     * Contains detailed information about the seedbed including members and activities.
     *
     * @param researchSeedbedProfileId the research seedbed profile identifier
     * @param academicPeriodId the academic period identifier for the report
     * @return Excel report with seedbed data and metadata
     */
    ExcelReport<SeedbedReportMetadata> getExcelBytesReport(Long researchSeedbedProfileId, Long academicPeriodId);
}
