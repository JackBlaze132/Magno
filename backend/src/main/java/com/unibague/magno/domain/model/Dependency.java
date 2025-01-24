package com.unibague.magno.domain.model;

public class Dependency {

    private Long id;
    private String name;

    public Dependency(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dependency() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
