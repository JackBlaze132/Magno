package com.unibague.magno.domain.model.enums;

/**
 * Enum representing the type of academic program.
 */
public enum AcademicProgramType {
    PREGRADO("Pregrado"), POSGRADO("Posgrado"), NO_DEFINIDO("No Definido");

    private final String formattedName;

    AcademicProgramType(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Returns the display-friendly name for this enum value.
     *
     * @return the formatted name
     */
    public String getFormattedName() {
        return formattedName;
    }
}
