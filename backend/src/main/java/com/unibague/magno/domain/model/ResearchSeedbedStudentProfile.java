package com.unibague.magno.domain.model;

public class ResearchSeedbedStudentProfile {

    private Long id;
    private Long researchSeedbedProfileId;
    private Long studentProfileId;
    private Boolean wasActive;

    public ResearchSeedbedStudentProfile(Long id, Long researchSeedbedId, Long studentProfileId, Boolean wasActive) {
        this.id = id;
        this.researchSeedbedProfileId = researchSeedbedId;
        this.studentProfileId = studentProfileId;
        this.wasActive = wasActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResearchSeedbedProfileId() {
        return researchSeedbedProfileId;
    }

    public void setResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        this.researchSeedbedProfileId = researchSeedbedProfileId;
    }

    public Long getStudentProfileId() {
        return studentProfileId;
    }

    public void setStudentProfileId(Long studentProfileId) {
        this.studentProfileId = studentProfileId;
    }

    public Boolean getWasActive() {
        return wasActive;
    }

    public void setWasActive(Boolean wasActive) {
        this.wasActive = wasActive;
    }
}
