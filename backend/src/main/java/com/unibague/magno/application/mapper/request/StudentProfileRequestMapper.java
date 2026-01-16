package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.domain.model.StudentProfile;

/**
 * Mapper interface for converting student profile request DTOs to domain models.
 * Manually implemented to fetch the student's semester and academic programs from Integra system.
 */
public interface StudentProfileRequestMapper {
    StudentProfile toStudentProfile(StudentProfileRequest studentProfileRequest);
}
