package com.unibague.magno.domain.model.certificates.projections;

/**
 * Projection interface for student seedbed certificate data.
 */
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
