package com.unibague.magno.domain.model.enums;

/**
 * Enum representing biological sex values used in the system.
 */
public enum Sex {
    MASCULINO("Masculino"), FEMENINO("Femenino");

    private final String formattedName;

    Sex(String formattedName) {
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
