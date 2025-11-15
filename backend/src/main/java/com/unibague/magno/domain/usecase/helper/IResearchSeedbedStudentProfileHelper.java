package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;

public interface IResearchSeedbedStudentProfileHelper {
    ResearchSeedbedStudentProfile verifyStudentHasAProfile(ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId);
}
