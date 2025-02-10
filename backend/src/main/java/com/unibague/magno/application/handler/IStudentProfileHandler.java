package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.application.dto.response.StudentProfileResponse;

import java.util.List;

public interface IStudentProfileHandler {
    StudentProfileResponse findById(Long id);
    StudentProfileResponse save(StudentProfileRequest studentProfile);
    StudentProfileResponse updateById(Long id, StudentProfileRequest studentProfile);
    void deleteById(Long id);
    List<StudentProfileResponse> findAll();
}
