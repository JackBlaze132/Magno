package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing investigation group profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving investigation group profiles.
 * An investigation group profile represents the state and configuration of an investigation group
 * within a specific academic period, including its coordinators and associated research seedbeds.
 * </p>
 */
public interface IInvestigationGroupProfilePersistencePort {
    Optional<InvestigationGroupProfile> findById(Long id);
    InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile);
    InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile);
    void deleteById(Long id);
    List<InvestigationGroupProfile> findAll();

    /**
     * Retrieves all investigation group profiles for a specific academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return a list of investigation group profiles associated with the academic period
     */
    List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId);

    /**
     * Generates an Excel report for investigation groups for a single academic period (half-year report).
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return an {@link ExcelReport} containing the report bytes and half-year metadata
     */
    ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId);

    /**
     * Generates an Excel report comparing investigation groups across two academic periods (annual report).
     *
     * @param academicPeriodId1 the unique identifier of the first academic period
     * @param academicPeriodId2 the unique identifier of the second academic period
     * @return an {@link ExcelReport} containing the report bytes and annual comparison metadata
     */
    ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualYearInvestigationGroupReport(Long academicPeriodId1, Long academicPeriodId2);

    /**
     * Generates an Excel report of active research seedbeds for a single academic period.
     *
     * @param academicPeriodId the unique identifier of the academic period
     * @return an {@link ExcelReport} containing the report bytes and active seedbeds metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId);

    /**
     * Generates an Excel report comparing active research seedbeds across two academic periods.
     *
     * @param academicPeriodId1 the unique identifier of the first academic period
     * @param academicPeriodId2 the unique identifier of the second academic period
     * @return an {@link ExcelReport} containing the report bytes and comparison metadata
     */
    ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2);
}
