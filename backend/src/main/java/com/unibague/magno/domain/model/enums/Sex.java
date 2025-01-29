package com.unibague.magno.domain.model.enums;

public enum Sex {
    MASCULINO("Masculino"), FEMENINO("Femenino");

    private final String formattedName;

    Sex(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
