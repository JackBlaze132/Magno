package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.InvestigationGroupProfile;

public interface IInvestigationGroupProfileHelper {
    InvestigationGroupProfile verifyUserHasFunctionaryProfile(InvestigationGroupProfile igp);
}
