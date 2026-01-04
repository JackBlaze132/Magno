package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.InvestigationGroupProfile;

public interface IInvestigationGroupProfileHelper {
    InvestigationGroupProfile verifyUserHasFunctionaryProfile(InvestigationGroupProfile igp);
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(Long coordinatorId, Long academicPeriodId);
}
