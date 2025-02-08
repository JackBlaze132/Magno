package com.unibague.magno.domain.model;

public class StudentProfile {

    private Long id;
    private Long userId;
    private Long academicPeriodId;
    private Byte semester;

    public StudentProfile(Long id, Long userId, Long academicPeriodId, Byte semester) {
        this.id = id;
        this.userId = userId;
        this.academicPeriodId = academicPeriodId;
        this.semester = semester;
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
}
