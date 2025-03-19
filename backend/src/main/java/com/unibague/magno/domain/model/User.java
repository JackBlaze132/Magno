package com.unibague.magno.domain.model;

import com.unibague.magno.domain.model.enums.Sex;

import java.util.Set;

public class User {

    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String userCode;
    private boolean isExternalUser;
    private Sex sex;

    public User(Long id, String fullName, String identificationNumber, String email,
                String userCode, boolean isExternalUser, Sex sex) {
        this.id = id;
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.userCode = userCode;
        this.isExternalUser = isExternalUser;
        this.sex = sex;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public boolean isExternalUser() {
        return isExternalUser;
    }

    public void setExternalUser(boolean externalUser) {
        isExternalUser = externalUser;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
