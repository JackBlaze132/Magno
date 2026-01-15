package com.unibague.magno.domain.model.enums;

/**
 * Enum representing the types of users that can be created from Integra requests.
 * <p>
 * This is primarily used by the /api/users/integra-user endpoint.
 * </p>
 */
public enum JSONIntegraType {
    FUNCIONARIO("Funcionario"), ESTUDIANTE("Estudiante");

    private final String formattedName;

    JSONIntegraType(String formattedName) {
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
