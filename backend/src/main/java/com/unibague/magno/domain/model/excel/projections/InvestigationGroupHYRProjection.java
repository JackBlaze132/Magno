package com.unibague.magno.domain.model.excel.projections;

/**
 * Projection interface for half-year investigation group report data.
 * <p>
 * The names of the methods in this interface should match the aliases used in the SQL query
 * defined in the repository.
 * </p>
 */
public interface InvestigationGroupHYRProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    String getResearchSeedbedName();
    String getCoordinatorName();
    Long getStudentCount();
    Boolean getIsActive();
}
