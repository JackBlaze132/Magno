package com.unibague.magno.domain.model;

public class ResearchSeedbedStudentProfile {

    private Long id;
    private Long researchSeedbedProfileId;
    private Long studentProfileId;
    private Boolean wasActive;
    private Boolean isLeader;

    public ResearchSeedbedStudentProfile(Long id, Long researchSeedbedId, Long studentProfileId,
                                         Boolean wasActive, Boolean isLeader) {
        this.id = id;
        this.researchSeedbedProfileId = researchSeedbedId;
        this.studentProfileId = studentProfileId;
        this.wasActive = wasActive;
        this.isLeader = isLeader;
    }

    public ResearchSeedbedStudentProfile() {
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

    public Boolean getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Boolean leader) {
        isLeader = leader;
    }
}
