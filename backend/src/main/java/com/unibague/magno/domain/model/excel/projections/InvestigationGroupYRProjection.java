package com.unibague.magno.domain.model.excel.projections;

/**
 * Projection interface for yearly investigation group report data.
 */
public interface InvestigationGroupYRProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    String getResearchSeedbedName();
    Long getStudentCount();
}
