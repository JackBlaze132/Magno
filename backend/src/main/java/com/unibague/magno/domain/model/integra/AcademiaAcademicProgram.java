package com.unibague.magno.domain.model.integra;

public class AcademiaAcademicProgram {
    private String programCode;
    private String programName;
    private String pensumCode;
    private String sedeName;
    private String methodology;
    private String modality;

    public AcademiaAcademicProgram() {
    }

    public AcademiaAcademicProgram(String programCode, String programName, String pensumCode, String sedeName, String methodology, String modality) {
        this.programCode = programCode;
        this.programName = programName;
        this.pensumCode = pensumCode;
        this.sedeName = sedeName;
        this.methodology = methodology;
        this.modality = modality;
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

    public String getPensumCode() {
        return pensumCode;
    }

    public void setPensumCode(String pensumCode) {
        this.pensumCode = pensumCode;
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

    @Override
    public String toString() {
        return "Ap2{" +
                "program_code='" + programCode + '\'' +
                ", program_name='" + programName + '\'' +
                ", pensum_code='" + pensumCode + '\'' +
                ", sede_name='" + sedeName + '\'' +
                ", methodology='" + methodology + '\'' +
                ", modality='" + modality + '\'' +
                '}';
    }
}
