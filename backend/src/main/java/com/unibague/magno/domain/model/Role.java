package com.unibague.magno.domain.model;

import java.util.Set;

public class Role {

    private Long id;
    private String name;
    private Set<Long> userIds;

    public Role(Long id, String name, Set<Long> userIds) {
        this.id = id;
        this.name = name;
        this.userIds = userIds;
    }

    public Role() {
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

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> usersIds) {
        this.userIds = usersIds;
    }
}
