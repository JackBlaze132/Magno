package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;

public class ResearchSeedbedStudentProfileHelper implements IResearchSeedbedStudentProfileHelper{

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;

    public ResearchSeedbedStudentProfileHelper(IIntegraServicePort integraServicePort,
                                               IUserServicePort userServicePort,
                                               IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort,
                                               IStudentProfileServicePort studentProfileServicePort) {
        this.integraServicePort = integraServicePort;
        this.userServicePort = userServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
    }

    @Override
    public ResearchSeedbedStudentProfile verifyStudentHasAProfile
            (ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        Long studentUserId = researchSeedbedStudentProfile.getStudentProfileId();
        Long researchSeedbedProfileId = researchSeedbedStudentProfile.getResearchSeedbedProfileId();

        ResearchSeedbedProfile researchSeedbedProfile =
                researchSeedbedProfileServicePort.findById(researchSeedbedProfileId);
        Long academicPeriodId = researchSeedbedProfile.getAcademicPeriodId();

        boolean hasProfile = studentProfileServicePort.existsByUserIdAndAcademicPeriodId(
                studentUserId, academicPeriodId);

        if (hasProfile) {
            studentProfileServicePort.findByUserIdAndAcademicPeriodId(studentUserId, academicPeriodId)
                    .ifPresent(profile -> researchSeedbedStudentProfile.setStudentProfileId(profile.getId()));
            return researchSeedbedStudentProfile;
        }

        User studentUser = userServicePort.findById(studentUserId);
        StudentProfile studentProfile = studentProfileServicePort
                .createStudentProfileFromIntegraData
                        (studentUser.getIdentificationNumber(), academicPeriodId, studentUser);

        researchSeedbedStudentProfile.setStudentProfileId(studentProfile.getId());
        return researchSeedbedStudentProfile;
    }
}
