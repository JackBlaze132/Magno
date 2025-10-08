package com.unibague.magno.domain.model.enums;

public enum SeedbedRole {
    ESTUDIANTE("Estudiante"),
    ESTUDIANTE_LIDER("Estudiante lider"),
    TUTOR_DE_SEMILLERO("Tutor de semillero"),
    COORDINADOR_DE_SEMILLERO("Coordinador de semillero"),
    COORDINADOR_DE_GRUPO_DE_INVESTIGACION("Coordinador de grupo de investigacion"),
    DIRI("DIRI");

    private final String formattedName;

    SeedbedRole(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
