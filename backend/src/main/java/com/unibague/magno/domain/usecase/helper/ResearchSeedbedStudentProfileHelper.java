package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.*;

/**
 * Implementation of {@link IResearchSeedbedStudentProfileHelper}.
 * <p>
 * Provides auxiliary operations for research seedbed student profile management,
 * including student profile verification and creation.
 * </p>
 *
 * @see IResearchSeedbedStudentProfileHelper
 */
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

    @Override
    public boolean verifyAcademicPeriodIsCurrentStatus(Long academicPeriodId) {
        AcademicPeriod ap = academicPeriodServicePort.findById(academicPeriodId);
        return !ap.isCurrent();
    }
}
