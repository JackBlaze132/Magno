package com.unibague.magno.domain.model.certificates.studentcertificates;

import java.util.List;

public class StudentSeedbedCertificate {

    private String studentName;
    private String identificationNumber;
    private String seedbedName;
    private String investigationGroupName;
    private List<StudentSeedbedParticipation> seedbedParticipations;

    public StudentSeedbedCertificate(String studentName, String identificationNumber, String seedbedName,
                                     String investigationGroupName, List<StudentSeedbedParticipation> seedbedParticipations) {
        this.studentName = studentName;
        this.identificationNumber = identificationNumber;
        this.seedbedName = seedbedName;
        this.investigationGroupName = investigationGroupName;
        this.seedbedParticipations = seedbedParticipations;
    }

    public StudentSeedbedCertificate() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getSeedbedName() {
        return seedbedName;
    }

    public void setSeedbedName(String seedbedName) {
        this.seedbedName = seedbedName;
    }

    public String getInvestigationGroupName() {
        return investigationGroupName;
    }

    public void setInvestigationGroupName(String investigationGroupName) {
        this.investigationGroupName = investigationGroupName;
    }

    public List<StudentSeedbedParticipation> getSeedbedParticipations() {
        return seedbedParticipations;
    }

    public void setSeedbedParticipations(List<StudentSeedbedParticipation> seedbedParticipations) {
        this.seedbedParticipations = seedbedParticipations;
    }
}
