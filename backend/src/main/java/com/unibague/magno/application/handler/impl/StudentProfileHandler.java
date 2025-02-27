package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.application.handler.interfaces.IStudentProfileHandler;
import com.unibague.magno.application.mapper.request.StudentProfileRequestMapper;
import com.unibague.magno.application.mapper.response.StudentProfileResponseMapper;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.model.StudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentProfileHandler implements IStudentProfileHandler {
    private final IStudentProfileServicePort studentProfileServicePort;
    private final StudentProfileRequestMapper studentProfileRequestMapper;
    private final StudentProfileResponseMapper studentProfileResponseMapper;

    @Override
    public StudentProfileResponse findById(Long id) {
        StudentProfile studentProfile = studentProfileServicePort.findById(id);
        return studentProfileResponseMapper.toResponse(studentProfile);
    }

    @Override
    public StudentProfileResponse save(StudentProfileRequest studentProfile) {
        return studentProfileResponseMapper.toResponse(studentProfileServicePort
                .save(studentProfileRequestMapper.toStudentProfile(studentProfile)));
    }

    @Override
    public StudentProfileResponse updateById(Long id, StudentProfileRequest studentProfile) {
        return studentProfileResponseMapper.toResponse(studentProfileServicePort
                .update(id, studentProfileRequestMapper.toStudentProfile(studentProfile)));
    }

    @Override
    public void deleteById(Long id) {
        studentProfileServicePort.deleteById(id);
    }

    @Override
    public List<StudentProfileResponse> findAll() {
        return studentProfileResponseMapper.toResponseList(studentProfileServicePort.findAll());
    }
}
