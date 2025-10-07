package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.dto.response.AcademicProgramResponse;
import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.StudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StudentProfileResponseMapperImpl implements StudentProfileResponseMapper{

    private final IUserServicePort userServicePort;
    private final UserResponseMapper userResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    private final IAcademicProgramServicePort academicProgramServicePort;
    private final AcademicProgramResponseMapper academicProgramResponseMapper;

    private final IRoleServicePort roleServicePort;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public StudentProfileResponse toResponse(StudentProfile studentProfile) {

        Long userId = studentProfile.getUserId();
        UserResponse userResponse = userResponseMapper.toResponse(userServicePort
                .findById(userId));

        Long academicPeriodId = studentProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriodResponse = academicPeriodResponseMapper
                .toResponse(academicPeriodServicePort
                .findById(academicPeriodId));

        Set<AcademicProgramResponse> academicProgramResponses = academicProgramResponseMapper
                .toResponseSet(academicProgramServicePort
                .findAcademicProgramsByIds(studentProfile.getAcademicProgramsIds()));

        return StudentProfileResponse.builder()
                .id(studentProfile.getId())
                .user(userResponse)
                .academicPeriod(academicPeriodResponse)
                .semester(studentProfile.getSemester())
                .academicPrograms(academicProgramResponses)
                .roleId(roleResponseMapper.toResponse(roleServicePort.findById(studentProfile.getRoleId())))
                .build();
    }

    @Override
    public List<StudentProfileResponse> toResponseList(List<StudentProfile> studentProfiles) {
        return studentProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
