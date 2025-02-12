package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.domain.model.StudentProfile;

public interface StudentProfileRequestMapper {
    StudentProfile toStudentProfile(StudentProfileRequest studentProfileRequest);
}
