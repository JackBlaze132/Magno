package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StudentProfileRequestMapperImpl implements StudentProfileRequestMapper {

    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;

    @Override
    public StudentProfile toStudentProfile(StudentProfileRequest studentProfileRequest) {
        if ( studentProfileRequest == null ) {
            return null;
        }

        User user = userServicePort.findById( studentProfileRequest.getUserId() );
        List<IntegraStudent> students = integraServicePort.getIntegraStudentByIdentification(user.getIdentificationNumber());

        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setUserId( studentProfileRequest.getUserId() );
        studentProfile.setAcademicPeriodId( studentProfileRequest.getAcademicPeriodId() );
        studentProfile.setSemester(Byte.valueOf(students.stream().findFirst().get().getSemester()));
        Set<Long> set = studentProfileRequest.getAcademicProgramsIds();
        if ( set != null ) {
            studentProfile.setAcademicProgramsIds( new LinkedHashSet<Long>( set ) );
        }

        return studentProfile;
    }
}
