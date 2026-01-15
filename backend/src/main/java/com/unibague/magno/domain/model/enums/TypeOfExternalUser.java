package com.unibague.magno.domain.model.enums;

/**
 * Enum representing external user types.
 */
public enum TypeOfExternalUser {

    ESTUDIANTE_DE_INTERCAMBIO("Estudiante de intercambio");

    private final String formattedName;

    TypeOfExternalUser(String formattedName) {
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
