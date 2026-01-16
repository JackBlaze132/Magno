package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.domain.model.StudentProfile;

import java.util.List;

/**
 * Mapper interface for converting student profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships (user, academic period, programs, role).
 */
public interface StudentProfileResponseMapper {
    StudentProfileResponse toResponse(StudentProfile studentProfile);
    List<StudentProfileResponse> toResponseList(List<StudentProfile> studentProfiles);
}
