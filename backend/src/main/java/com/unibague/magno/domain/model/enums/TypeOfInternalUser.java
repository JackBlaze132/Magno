package com.unibague.magno.domain.model.enums;

/**
 * Enum representing internal user types.
 */
public enum TypeOfInternalUser {

    ESTUDIANTE("Estudiante"), FUNCIONARIO("Funcionario");

    private final String formattedName;

    TypeOfInternalUser(String formattedName) {
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
