package com.unibague.magno.domain.model.enums;

public enum TypeOfExternalUser {

    ESTUDIANTE_DE_INTERCAMBIO("Estudiante de intercambio");

    private final String formattedName;

    TypeOfExternalUser(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
