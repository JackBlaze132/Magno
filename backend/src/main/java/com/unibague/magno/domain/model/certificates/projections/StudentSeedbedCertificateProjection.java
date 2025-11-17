package com.unibague.magno.domain.model.certificates.projections;

public interface StudentSeedbedCertificateProjection {
    String getStudentName();
    String getIdentificationNumber();
    String getSeedbedName();
    String getInvestigationGroupName();
    String getStartDate();
    String getEndDate();
    String getSeedbedCoordinatorName();
    String getInvestigationGroupCoordinatorName();

}
