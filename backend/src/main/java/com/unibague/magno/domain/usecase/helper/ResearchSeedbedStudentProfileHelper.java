package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.FunctionaryNotAllowedToGenerateCertificateException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;

public class ResearchSeedbedStudentProfileHelper implements IResearchSeedbedStudentProfileHelper{

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final IResearchSeedbedServicePort researchSeedbedServicePort;
    private final IStudentProfileServicePort studentProfileServicePort;

    public ResearchSeedbedStudentProfileHelper(IIntegraServicePort integraServicePort,
                                               IUserServicePort userServicePort,
                                               IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort,
                                               IStudentProfileServicePort studentProfileServicePort,
                                               IResearchSeedbedServicePort researchSeedbedServicePort) {
        this.integraServicePort = integraServicePort;
        this.userServicePort = userServicePort;
        this.researchSeedbedProfileServicePort = researchSeedbedProfileServicePort;
        this.studentProfileServicePort = studentProfileServicePort;
        this.researchSeedbedServicePort = researchSeedbedServicePort;
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
    public StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId) {

        User studentUser = userServicePort.findById(userId);
        boolean isFunctionaryOrExternal = studentUser.getTypeOfInternalUser().equals(TypeOfInternalUser.FUNCIONARIO)
                || studentUser.getTypeOfInternalUser() == null;

        if (isFunctionaryOrExternal) {
            throw new FunctionaryNotAllowedToGenerateCertificateException
                    ("Functionaries or external users are not allowed to generate student seedbed certificates.");
        }

        ResearchSeedbed researchSeedbed = researchSeedbedServicePort.findById(researchSeedbedId);
        return null;
    }
}
