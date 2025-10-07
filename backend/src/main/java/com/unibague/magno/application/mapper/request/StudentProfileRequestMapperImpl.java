package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentProfileRequestMapperImpl implements StudentProfileRequestMapper {

    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IAcademicProgramServicePort academicProgramServicePort;

    @Override
    public StudentProfile toStudentProfile(StudentProfileRequest studentProfileRequest) {

        if ( studentProfileRequest == null ) {
            return null;
        }

        User user = userServicePort.findById( studentProfileRequest.getUserId() );
        List<IntegraStudent> students = integraServicePort.getIntegraStudentRecordsByIdentification(user.getIdentificationNumber());

        byte semester = integraServicePort.getMaxSemester(students);

        Set<String> academicProgramCodes = students.stream()
                .map(IntegraStudent::getProgramCode)
                .collect(Collectors.toSet());
        Set<Long> academicProgramIds = academicProgramServicePort.findAcademicProgramsByAcademicProgramCodes(academicProgramCodes)
                .stream()
                .map(AcademicProgram::getId)
                .collect(Collectors.toSet());

        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setUserId( studentProfileRequest.getUserId() );
        studentProfile.setAcademicPeriodId( studentProfileRequest.getAcademicPeriodId() );
        studentProfile.setSemester(semester);
        studentProfile.setAcademicProgramsIds(academicProgramIds);
        studentProfile.setRoleId(studentProfileRequest.getRoleId());

        return studentProfile;
    }
}
