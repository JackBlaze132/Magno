package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.StudentProfile;

import java.util.List;
import java.util.Optional;

public interface IStudentProfilePersistencePort {
    Optional<StudentProfile> findById(Long id);
    StudentProfile save(StudentProfile studentProfile);
    StudentProfile update(Long id, StudentProfile studentProfile);
    Optional<StudentProfile> findByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    void deleteById(Long id);
    List<StudentProfile> findAll();
}
