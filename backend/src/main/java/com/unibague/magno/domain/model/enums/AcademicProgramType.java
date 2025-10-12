package com.unibague.magno.domain.model.enums;

public enum AcademicProgramType {
    PREGRADO("Pregrado"), POSGRADO("Posgrado"), NO_DEFINIDO("No Definido");

    private final String formattedName;

    AcademicProgramType(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
