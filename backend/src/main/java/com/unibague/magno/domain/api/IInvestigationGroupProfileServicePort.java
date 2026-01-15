package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;

import java.util.List;

/**
 * Service port interface that defines the contract for investigation group profile management operations.
 * <p>
 * This interface provides methods for CRUD operations, queries, and Excel report generation related to
 * investigation group profiles, which represent the state and activities of research groups during
 * specific academic periods.
 * </p>
 *
 * @see InvestigationGroupProfile
 */
public interface IInvestigationGroupProfileServicePort {
    
    /**
     * Retrieves an investigation group profile by its unique identifier.
     *
     * @param id the unique identifier of the investigation group profile
     * @return the investigation group profile with the specified ID
     */
    InvestigationGroupProfile findById(Long id);
    
    /**
     * Persists a new investigation group profile.
     *
     * @param investigationGroupProfile the investigation group profile to save
     * @return the saved investigation group profile
     */
    InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile);
    
    /**
     * Updates an existing investigation group profile.
     *
     * @param id the unique identifier of the investigation group profile to update
     * @param investigationGroupProfile the investigation group profile data to update
     * @return the updated investigation group profile
     */
    InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile);
    
    /**
     * Deletes an investigation group profile by its unique identifier.
     *
     * @param id the unique identifier of the investigation group profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all investigation group profiles in the system.
     *
     * @return a list of all investigation group profiles
     */
    List<InvestigationGroupProfile> findAll();
    
    /**
     * Retrieves all investigation group profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of investigation group profiles for the specified period
     */
    List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    /**
     * Generates an Excel report for investigation group activities during a half-year period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return an Excel report containing investigation group half-year report metadata
     */
    ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId);
    
    /**
     * Generates an Excel report for investigation group activities during an annual period.
     *
     * @param academicPeriodId1 the unique identifier of the first academic period
     * @param academicPeriodId2 the unique identifier of the second academic period
     * @return an Excel report containing investigation group annual report metadata
     */
    ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualYearInvestigationGroupReport(Long academicPeriodId1, Long academicPeriodId2);

    /**
     * Generates an Excel report for active seedbeds during a half-year period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return an Excel report containing active seedbeds metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId);

    /**
     * Generates an Excel report for active seedbeds during an annual period.
     *
     * @param academicPeriodId1 the unique identifier of the first academic period
     * @param academicPeriodId2 the unique identifier of the second academic period
     * @return an Excel report containing active seedbeds metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2);
}
