package com.unibague.magno.domain.model.integra;

/**
 * This class represents the json returned by the API "Integra" (10/02/25) Although not all attributes are used
 * they are stored in case they are needed in the future.
 */
public class IntegraStudent {
    private String name;
    private String codeStudent;
    private String identification;
    private String programCode;
    private String program;
    private String formation;
    private String email;
    private String category;
    private String status;
    private String semester;
    private String telephone;
    private String sexo;

    public IntegraStudent(String name, String codeStudent, String identification, String programCode,
                          String formation, String email, String category, String status,
                          String semester, String telephone, String sexo) {
        this.name = name;
        this.codeStudent = codeStudent;
        this.identification = identification;
        this.programCode = programCode;
        this.formation = formation;
        this.email = email;
        this.category = category;
        this.status = status;
        this.semester = semester;
        this.telephone = telephone;
        this.sexo = sexo;
    }

    public IntegraStudent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeStudent() {
        return codeStudent;
    }

    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
