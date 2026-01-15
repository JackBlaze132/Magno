package com.unibague.magno.domain.model.enums;

/**
 * Enum representing roles available within research seedbeds.
 */
public enum SeedbedRole {
    USUARIO_SIN_ROL("Usuario sin rol", "Rol de usuario que se loguea por primera vez o no tiene rol asignado"),
    ESTUDIANTE("Estudiante", "Rol de estudiante"),
    ESTUDIANTE_LIDER("Estudiante lider", "Rol de estudiante lider"),
    TUTOR_DE_SEMILLERO("Tutor de semillero", "Rol de tutor de semillero"),
    COORDINADOR_DE_SEMILLERO("Coordinador de semillero", "Rol de coordinador de semillero"),
    COORDINADOR_DE_GRUPO_DE_INVESTIGACION("Coordinador de grupo de investigacion", "Rol de coordinador de grupo de investigacion"),
    DIRI("DIRI", "Rol de los usuarios de la direccion de investigaciones");

    private final String formattedName;
    private final String description;

    SeedbedRole(String formattedName, String description) {
        this.formattedName = formattedName;
        this.description = description;
    }

    /**
     * Returns the display-friendly name for this role.
     *
     * @return the formatted name
     */
    public String getFormattedName() {
        return formattedName;
    }

    /**
     * Returns the authority string used by the security layer.
     *
     * @return the authority string in the format ROLE_ENUM_NAME
     */
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    /**
     * Returns the description for this role.
     *
     * @return the role description
     */
    public String getDescription() {
        return description;
    }
}
