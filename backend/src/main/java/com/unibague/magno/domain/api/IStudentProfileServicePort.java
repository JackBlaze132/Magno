package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.StudentProfile;

import java.util.List;

public interface IStudentProfileServicePort {
    StudentProfile findById(Long id);
    StudentProfile save(StudentProfile studentProfile);
    StudentProfile update(Long id, StudentProfile studentProfile);
    void deleteById(Long id);
    List<StudentProfile> findAll();
}
