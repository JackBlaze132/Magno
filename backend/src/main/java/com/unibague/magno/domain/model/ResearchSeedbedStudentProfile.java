package com.unibague.magno.domain.model;

public class ResearchSeedbedStudentProfile {

    private Long id;
    private Long researchSeedbedId;
    private Long studentProfileId;
    private Boolean wasActive;

    public ResearchSeedbedStudentProfile(Long id, Long researchSeedbedId, Long studentProfileId, Boolean wasActive) {
        this.id = id;
        this.researchSeedbedId = researchSeedbedId;
        this.studentProfileId = studentProfileId;
        this.wasActive = wasActive;
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
