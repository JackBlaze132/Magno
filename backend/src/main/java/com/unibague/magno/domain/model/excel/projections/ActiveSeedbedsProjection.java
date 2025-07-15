package com.unibague.magno.domain.model.excel.projections;

public interface ActiveSeedbedsProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    Long getActiveSeedbedsCount();
    Long getActiveStudentsCount();
}
