package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;

public interface IResearchSeedbedProfileHelper {
    ResearchSeedbedProfile verifyUsersHasFunctionaryProfiles(ResearchSeedbedProfile rsp);
    void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage);
}
