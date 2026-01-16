package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.application.dto.response.StudentProfileResponse;

import java.util.List;

/**
 * Handler interface for student profile operations.
 * Manages student profiles which represent a student's participation
 * in the system for specific academic periods.
 */
public interface IStudentProfileHandler {
    StudentProfileResponse findById(Long id);
    StudentProfileResponse save(StudentProfileRequest studentProfile);
    StudentProfileResponse updateById(Long id, StudentProfileRequest studentProfile);
    void deleteById(Long id);
    List<StudentProfileResponse> findAll();

    /**
     * Retrieves all student profiles for a specific user across all periods.
     *
     * @param userId the user identifier
     * @return list of student profiles for the specified user
     */
    List<StudentProfileResponse> findAllProfilesByUserId(Long userId);

    /**
     * Retrieves all student profiles for a specific academic period.
     *
     * @param academicPeriodId the academic period identifier
     * @return list of student profiles in the specified period
     */
    List<StudentProfileResponse> findAllProfilesByAcademicPeriodId(Long academicPeriodId);
}
