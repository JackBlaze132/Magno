package com.unibague.magno.domain.model.excel.projections;

/**
 * Projection interface for half-year seedbed report data.
 * <p>
 * The names of the methods in this interface should match the aliases used in the SQL query
 * defined in the repository.
 * </p>
 */
public interface SeedbedReportProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    String getResearchSeedbedName();
    String getCoordinatorName();
    String getStudentName();
    String getCode();
    String getAcademicProgramName();
    Integer getSemester();
    String getSex();
}


