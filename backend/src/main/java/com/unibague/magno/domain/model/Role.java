package com.unibague.magno.domain.model;

import com.unibague.magno.domain.model.enums.SeedbedRole;

public class Role {

    private Long id;
    private SeedbedRole name;
    private String description;

    public Role(Long id, SeedbedRole name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeedbedRole getName() {
        return name;
    }

    public void setName(SeedbedRole name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormattedName() {
        return getName().getFormattedName();
    }
}
