package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;

import java.util.List;

public interface IInvestigationGroupProfileHandler {
    InvestigationGroupProfileResponse findById(Long id);
    InvestigationGroupProfileResponse save(InvestigationGroupProfileRequest investigationGroupProfileRequest);
    InvestigationGroupProfileResponse updateById(Long id, InvestigationGroupProfileRequest investigationGroupProfileRequest);
    void deleteById(Long id);
    List<InvestigationGroupProfileResponse> findAll();
    List<InvestigationGroupProfileResponse> findAllByAcademicPeriodId(Long id);
}
