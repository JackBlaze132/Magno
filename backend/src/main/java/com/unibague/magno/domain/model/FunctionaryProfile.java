package com.unibague.magno.domain.model;

import java.util.Set;

public class FunctionaryProfile {

    private Long id;
    private Long userId;
    private Long academicPeriodId;
    private Long dependencyId;
    private Set<Long> roleIds;

    public FunctionaryProfile(Long id, Long userId, Long academicPeriodId, Long dependencyId, Set<Long> roleIds) {
        this.id = id;
        this.userId = userId;
        this.academicPeriodId = academicPeriodId;
        this.dependencyId = dependencyId;
        this.roleIds = roleIds;
    }

    public FunctionaryProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAcademicPeriodId() {
        return academicPeriodId;
    }

    public void setAcademicPeriodId(Long academicPeriodId) {
        this.academicPeriodId = academicPeriodId;
    }

    public Long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(Long dependencyId) {
        this.dependencyId = dependencyId;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
