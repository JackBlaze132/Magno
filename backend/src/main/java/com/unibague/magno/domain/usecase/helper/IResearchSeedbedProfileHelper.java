package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;

import java.util.List;

public interface IResearchSeedbedProfileHelper {
    ResearchSeedbedProfile verifyUsersHasFunctionaryProfiles(ResearchSeedbedProfile rsp);
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);

    void handleFunctionaryProfileChangesOnCoordinatorUpdate
            (List<ResearchSeedbedProfile> researchSeedbedProfiles, Long academicPeriodId,
             Long oldCoordinatorId);
}
