package com.unibague.magno.domain.model;

/**
 * Domain model representing a functionary profile for an academic period.
 */
public class FunctionaryProfile {

    private Long id;
    private Long userId;
    private Long academicPeriodId;
    private Long dependencyId;
    private Long roleId;

    public FunctionaryProfile(Long id, Long userId, Long academicPeriodId, Long dependencyId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.academicPeriodId = academicPeriodId;
        this.dependencyId = dependencyId;
        this.roleId = roleId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
