package com.unibague.magno.domain.model;

public class ResearchSeedbedProfile {

    private Long id;
    private Long researchSeedbedId;
    private Long coordinatorId;
    private Long tutorId;
    private Long investigationGroupProfileId;
    private Long academicPeriodId;
    private Boolean wasActive;

    public ResearchSeedbedProfile(Long id, Long researchSeedbedId, Long coordinatorId, Long tutorId,
                                  Long investigationGroupProfileId, Long academicPeriodId, Boolean wasActive) {
        this.id = id;
        this.researchSeedbedId = researchSeedbedId;
        this.coordinatorId = coordinatorId;
        this.tutorId = tutorId;
        this.investigationGroupProfileId = investigationGroupProfileId;
        this.academicPeriodId = academicPeriodId;
        this.wasActive = wasActive;
    }

    public ResearchSeedbedProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResearchSeedbedId() {
        return researchSeedbedId;
    }

    public void setResearchSeedbedId(Long researchSeedbedId) {
        this.researchSeedbedId = researchSeedbedId;
    }

    public Long getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(Long coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public Long getInvestigationGroupProfileId() {
        return investigationGroupProfileId;
    }

    public void setInvestigationGroupProfileId(Long investigationGroupProfileId) {
        this.investigationGroupProfileId = investigationGroupProfileId;
    }

    public Long getAcademicPeriodId() {
        return academicPeriodId;
    }

    public void setAcademicPeriodId(Long academicPeriodId) {
        this.academicPeriodId = academicPeriodId;
    }

    public Boolean getWasActive() {
        return wasActive;
    }

    public void setWasActive(Boolean wasActive) {
        this.wasActive = wasActive;
    }
}
