package com.unibague.magno.domain.model.excel.projections;

/**
 * Projection interface for active seedbeds report data.
 */
public interface ActiveSeedbedsProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    Long getActiveSeedbedsCount();
    Long getActiveStudentsCount();
}
