package com.unibague.magno.domain.model;

/**
 * Domain model representing an investigation group profile for an academic period.
 */
public class InvestigationGroupProfile {

    private Long id;
    private Long investigationGroupId;
    private Long coordinatorId;
    private Long academicPeriodId;

    public InvestigationGroupProfile(Long id, Long investigationGroupId, Long coordinatorId, Long academicPeriodId) {
        this.id = id;
        this.investigationGroupId = investigationGroupId;
        this.coordinatorId = coordinatorId;
        this.academicPeriodId = academicPeriodId;
    }

    public InvestigationGroupProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvestigationGroupId() {
        return investigationGroupId;
    }

    public void setInvestigationGroupId(Long investigationGroupId) {
        this.investigationGroupId = investigationGroupId;
    }

    public Long getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(Long coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public Long getAcademicPeriodId() {
        return academicPeriodId;
    }

    public void setAcademicPeriodId(Long academicPeriodId) {
        this.academicPeriodId = academicPeriodId;
    }
}
