package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.InvestigationGroupProfile;

import java.util.List;

public interface IInvestigationGroupProfileHelper {
    InvestigationGroupProfile verifyUserHasFunctionaryProfile(InvestigationGroupProfile igp);
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(Long coordinatorId, Long academicPeriodId);
    void handleFunctionaryProfileChangeOnDelete(List<InvestigationGroupProfile> investigationGroupProfiles, Long functionaryProfileId);
    void handleFunctionaryProfileChangeOnUpdate(Long oldCoordinatorId, Long academicPeriodId, Long investigationGroupProfileId);
}
