package com.unibague.magno.domain.model.enums;

public enum TypeOfInternalUser {

    ESTUDIANTE("Estudiante"), FUNCIONARIO("Funcionario");

    private final String formattedName;

    TypeOfInternalUser(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
