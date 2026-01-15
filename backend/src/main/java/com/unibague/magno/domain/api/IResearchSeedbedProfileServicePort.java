package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;

import java.util.List;

/**
 * Service port interface that defines the contract for research seedbed profile management operations.
 * <p>
 * This interface provides methods for CRUD operations, queries, and Excel report generation related to
 * research seedbed profiles, which represent the state and activities of research seedbeds during
 * specific academic periods.
 * </p>
 *
 * @see ResearchSeedbedProfile
 */
public interface IResearchSeedbedProfileServicePort {
    
    /**
     * Retrieves a research seedbed profile by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed profile
     * @return the research seedbed profile with the specified ID
     */
    ResearchSeedbedProfile findById(Long id);
    
    /**
     * Persists a new research seedbed profile.
     *
     * @param researchSeedbedProfile the research seedbed profile to save
     * @return the saved research seedbed profile
     */
    ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile);
    
    /**
     * Updates an existing research seedbed profile.
     *
     * @param id the unique identifier of the research seedbed profile to update
     * @param researchSeedbedProfile the research seedbed profile data to update
     * @return the updated research seedbed profile
     */
    ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile);
    
    /**
     * Deletes a research seedbed profile by its unique identifier.
     *
     * @param id the unique identifier of the research seedbed profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all research seedbed profiles in the system.
     *
     * @return a list of all research seedbed profiles
     */
    List<ResearchSeedbedProfile> findAll();
    
    /**
     * Retrieves all research seedbed profiles associated with a specific investigation group profile.
     *
     * @param id the unique identifier of the investigation group profile
     * @return a list of research seedbed profiles for the specified investigation group profile
     */
    List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id);
    
    /**
     * Retrieves all research seedbed profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of research seedbed profiles for the specified period
     */
    List<ResearchSeedbedProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    /**
     * Generates an Excel report for a specific research seedbed profile.
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @param academicPeriodId the unique identifier of the academic period
     * @return an Excel report containing seedbed report metadata
     */
    ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId);
}
