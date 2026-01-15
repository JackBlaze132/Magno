package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing research seedbed profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving research seedbed profiles.
 * A research seedbed profile represents the configuration and state of a research seedbed
 * within a specific academic period, linking it to an investigation group profile.
 * </p>
 */
public interface IResearchSeedbedProfilePersistencePort {
    Optional<ResearchSeedbedProfile> findById(Long id);
    ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile);
    ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile);
    void deleteById(Long id);
    List<ResearchSeedbedProfile> findAll();

    /**
     * Retrieves all research seedbed profiles associated with a specific investigation group profile.
     *
     * @param id the unique identifier of the investigation group profile
     * @return a list of research seedbed profiles belonging to the investigation group profile
     */
    List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id);

    /**
     * Retrieves all research seedbed profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of research seedbed profiles associated with the academic period
     */
    List<ResearchSeedbedProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    /**
     * Generates an Excel report for a specific research seedbed profile.
     * <p>
     * The report contains detailed information about the seedbed including
     * participants, activities, and other relevant data for the specified academic period.
     * </p>
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @param academicPeriodId         the unique identifier of the academic period
     * @return an {@link ExcelReport} containing the report bytes and metadata
     */
    ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId);
}
