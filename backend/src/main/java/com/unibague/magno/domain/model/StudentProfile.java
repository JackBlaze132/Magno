package com.unibague.magno.domain.model;

import java.util.Set;

public class StudentProfile {

    private Long id;
    private Long userId;
    private Long academicPeriodId;
    private Byte semester;
    private Set<Long> academicProgramsIds;
    private Set<Long> roleIds;

    public StudentProfile(Long id, Long userId, Long academicPeriodId, Byte semester, Set<Long> academicProgramsIds) {
        this.id = id;
        this.userId = userId;
        this.academicPeriodId = academicPeriodId;
        this.semester = semester;
        this.academicProgramsIds = academicProgramsIds;
    }

    public StudentProfile() {
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

    public Byte getSemester() {
        return semester;
    }

    public void setSemester(Byte semester) {
        this.semester = semester;
    }

    public Set<Long> getAcademicProgramsIds() {
        return academicProgramsIds;
    }

    public void setAcademicProgramsIds(Set<Long> academicProgramsIds) {
        this.academicProgramsIds = academicProgramsIds;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
