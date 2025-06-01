package com.unibague.magno.domain.model.projections;

// This interface is used to define the structure of the data returned by the seedbed report query.
// The name of the variables in this interface should match the aliases used in the SQL query defined in the repository.
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


