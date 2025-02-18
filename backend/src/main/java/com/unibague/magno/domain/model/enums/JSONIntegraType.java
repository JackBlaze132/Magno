package com.unibague.magno.domain.model.enums;

/**
 * This enum is used to represent the types of users that can be created in the system
 * most specifically in the endpoint (POST) /api/users/integra-user
 */
public enum JSONIntegraType {
    FUNCIONARIO("Funcionario"), ESTUDIANTE("Estudiante");

    private final String formattedName;

    JSONIntegraType(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
