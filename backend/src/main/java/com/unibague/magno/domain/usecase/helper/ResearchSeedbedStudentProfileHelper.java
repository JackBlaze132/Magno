package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.*;

public class ResearchSeedbedStudentProfileHelper implements IResearchSeedbedStudentProfileHelper{

    private final IUserServicePort userServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;
    private final IAcademicPeriodServicePort academicPeriodServicePort;

    public ResearchSeedbedStudentProfileHelper(IUserServicePort userServicePort,
                                               IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort,
                                               IStudentProfileServicePort studentProfileServicePort,
                                               IAcademicPeriodServicePort academicPeriodServicePort) {
        this.userServicePort = userServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
        this.academicPeriodServicePort = academicPeriodServicePort;
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

    /**
     * Verifies if the academic period is not current, if not, returns true, which means it cannot be used to
     * create, update or delete a researchSeedbedStudentProfile associated to  the academic period.
     * @param academicPeriodId The ID of the academic period to verify
     * @return true if the academic period is not current, false otherwise
     */
    @Override
    public boolean verifyAcademicPeriodIsCurrentStatus(Long academicPeriodId) {
        AcademicPeriod ap = academicPeriodServicePort.findById(academicPeriodId);
        return !ap.isCurrent();
    }
}
