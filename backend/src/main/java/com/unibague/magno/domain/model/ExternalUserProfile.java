package com.unibague.magno.domain.model;

import com.unibague.magno.domain.model.enums.TypeOfExternalUser;

public class ExternalUserProfile {

    private Long id;
    private Long userId;
    private Long academicPeriodId;
    private Long researchSeedbedProfileId;
    private String country;
    private TypeOfExternalUser typeOfExternalUser;

    public ExternalUserProfile(Long id, Long userId, Long academicPeriodId, Long researchSeedbedProfileId,
                               String country, TypeOfExternalUser typeOfExternalUser) {
        this.id = id;
        this.userId = userId;
        this.academicPeriodId = academicPeriodId;
        this.researchSeedbedProfileId = researchSeedbedProfileId;
        this.country = country;
        this.typeOfExternalUser = typeOfExternalUser;
    }

    public ExternalUserProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAcademicPeriodId() {
        return academicPeriodId;
    }

    public void setAcademicPeriodId(Long academicPeriodId) {
        this.academicPeriodId = academicPeriodId;
    }

    public Long getResearchSeedbedProfileId() {
        return researchSeedbedProfileId;
    }

    public void setResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        this.researchSeedbedProfileId = researchSeedbedProfileId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TypeOfExternalUser getTypeOfExternalUser() {
        return typeOfExternalUser;
    }

    public void setTypeOfExternalUser(TypeOfExternalUser typeOfExternalUser) {
        this.typeOfExternalUser = typeOfExternalUser;
    }
}
