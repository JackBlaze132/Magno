package com.unibague.magno.domain.model.enums;

public enum AcademicProgramType {
    PREGRADO("Pregrado"), POSGRADO("Posgrado");

    private final String formattedName;

    AcademicProgramType(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
