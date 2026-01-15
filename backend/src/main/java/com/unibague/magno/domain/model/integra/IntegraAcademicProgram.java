package com.unibague.magno.domain.model.integra;

/**
 * Domain model representing an academic program record from the Integra system.
 */
public class IntegraAcademicProgram {

    private String programCode;
    private String programName;
    private String sedeCode;
    private String sedeName;
    private String methodology;
    private String modality;
    private String formation;

    public IntegraAcademicProgram(String programCode, String programName, String sedeCode, String sedeName,
                                  String methodology, String modality, String formation) {
        this.programCode = programCode;
        this.programName = programName;
        this.sedeCode = sedeCode;
        this.sedeName = sedeName;
        this.methodology = methodology;
        this.modality = modality;
        this.formation = formation;
    }

    public IntegraAcademicProgram() {
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getSedeCode() {
        return sedeCode;
    }

    public void setSedeCode(String sedeCode) {
        this.sedeCode = sedeCode;
    }

    public String getSedeName() {
        return sedeName;
    }

    public void setSedeName(String sedeName) {
        this.sedeName = sedeName;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }
}
