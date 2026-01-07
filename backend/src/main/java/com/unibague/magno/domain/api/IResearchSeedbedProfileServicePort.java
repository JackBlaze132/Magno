package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;

import java.util.List;

public interface IResearchSeedbedProfileServicePort {
    ResearchSeedbedProfile findById(Long id);
    ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile);
    ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile);
    void deleteById(Long id);
    List<ResearchSeedbedProfile> findAll();
    List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id);
    List<ResearchSeedbedProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId);
}
