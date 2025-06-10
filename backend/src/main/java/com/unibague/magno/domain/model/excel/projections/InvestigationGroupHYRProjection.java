package com.unibague.magno.domain.model.excel.projections;

// This interface is used to define the structure of the data returned by the half year investigation group report query.
// The name of the variables in this interface should match the aliases used in the SQL query defined in the repository.
public interface InvestigationGroupHYRProjection {
    String getAcademicPeriodName();
    String getInvestigationGroupName();
    String getResearchSeedbedName();
    String getCoordinatorName();
    Long getStudentCount();
    Boolean getIsActive();
}
