package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.domain.model.StudentProfile;

import java.util.List;

public interface StudentProfileResponseMapper {
    StudentProfileResponse toResponse(StudentProfile studentProfile);
    List<StudentProfileResponse> toResponseList(List<StudentProfile> studentProfiles);
}
