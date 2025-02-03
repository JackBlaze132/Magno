package com.unibague.magno.domain.model.integra;

/**
 * This class represents the json returned by the API "Integra" (03/02/25) Although not all attributes are used
 * they are stored in case they are needed in the future.
 */
public class IntegraFunctionary {

    private String name;
    private String lastName;
    private String fullName;
    private String identification;
    private String email;
    private String codeUser;
    private String category;
    private String faculty;
    private String program;
    private String depCode;
    private String sede;
    private String dnsPhoto;
    private String dirPhoto;
    private String idPhoto;
    private String birthDate;
    private String dateAdmission;
    private String extension;
    private String codePosition;
    private String position;
    private String sex;

    public IntegraFunctionary(String name, String lastName, String fullName, String identification,
                              String email, String codeUser, String category, String faculty,
                              String program, String depCode, String sede, String dnsPhoto,
                              String dirPhoto, String idPhoto, String birthDate, String dateAdmission,
                              String extension, String codePosition, String position, String sex) {
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.identification = identification;
        this.email = email;
        this.codeUser = codeUser;
        this.category = category;
        this.faculty = faculty;
        this.program = program;
        this.depCode = depCode;
        this.sede = sede;
        this.dnsPhoto = dnsPhoto;
        this.dirPhoto = dirPhoto;
        this.idPhoto = idPhoto;
        this.birthDate = birthDate;
        this.dateAdmission = dateAdmission;
        this.extension = extension;
        this.codePosition = codePosition;
        this.position = position;
        this.sex = sex;
    }

    public IntegraFunctionary() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(String codeUser) {
        this.codeUser = codeUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getDnsPhoto() {
        return dnsPhoto;
    }

    public void setDnsPhoto(String dnsPhoto) {
        this.dnsPhoto = dnsPhoto;
    }

    public String getDirPhoto() {
        return dirPhoto;
    }

    public void setDirPhoto(String dirPhoto) {
        this.dirPhoto = dirPhoto;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDateAdmission() {
        return dateAdmission;
    }

    public void setDateAdmission(String dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCodePosition() {
        return codePosition;
    }

    public void setCodePosition(String codePosition) {
        this.codePosition = codePosition;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
