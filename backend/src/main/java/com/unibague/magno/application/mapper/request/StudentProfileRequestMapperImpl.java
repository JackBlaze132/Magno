package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraStudentNotFoundException;
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
        List<IntegraStudent> students = integraServicePort.getIntegraStudentByIdentification(user.getIdentificationNumber());
        if (students.isEmpty()) {
            throw new IntegraStudentNotFoundException(
                    String.format("Student with identification number %s not found in Integra", user.getIdentificationNumber())
            );
        }

        byte semester = (byte) students.stream()
                .map(IntegraStudent::getSemester)
                .map(s -> (s == null || s.isEmpty()) ? "0" : s)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(-1);

        Set<AcademicProgram> academicProgramSet = academicProgramServicePort
                .findAcademicProgramsByAcademicProgramCodes(
                students.stream()
                        .map(IntegraStudent::getProgramCode)
                        .collect(Collectors.toSet()));
        Set<Long> academicProgramIds = academicProgramSet.stream()
                .map(AcademicProgram::getId)
                .collect(Collectors.toSet());

        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setUserId( studentProfileRequest.getUserId() );
        studentProfile.setAcademicPeriodId( studentProfileRequest.getAcademicPeriodId() );
        studentProfile.setSemester(semester);
        studentProfile.setAcademicProgramsIds(academicProgramIds);

        return studentProfile;
    }
}
