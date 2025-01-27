package com.unibague.magno.domain.model;

public class User {

    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String user_code;
    private boolean isExternalUser;
    Sex sex;

    public User(Long id, String fullName, String identificationNumber, String email, String user_code,
                boolean isExternalUser, Sex sex) {
        this.id = id;
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.user_code = user_code;
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

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
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
