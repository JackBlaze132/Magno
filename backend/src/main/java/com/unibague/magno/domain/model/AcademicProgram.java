package com.unibague.magno.domain.model;

import com.unibague.magno.domain.model.enums.AcademicProgramType;

public class AcademicProgram {

    private Long id;
    private String name;
    private String programCode;
    private AcademicProgramType type;

    public AcademicProgram(Long id, String name, String code, AcademicProgramType type) {
        this.id = id;
        this.name = name;
        this.programCode = code;
        this.type = type;
    }

    public AcademicProgram() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public AcademicProgramType getType() {
        return type;
    }

    public void setType(AcademicProgramType type) {
        this.type = type;
    }
}
