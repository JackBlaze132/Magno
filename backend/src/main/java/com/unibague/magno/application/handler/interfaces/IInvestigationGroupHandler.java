package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;

import java.util.List;

public interface IInvestigationGroupHandler {
    InvestigationGroupResponse findById(Long id);
    InvestigationGroupResponse save(InvestigationGroupRequest investigationGroup);
    InvestigationGroupResponse updateById(Long id, InvestigationGroupRequest investigationGroup);
    void deleteById(Long id);
    List<InvestigationGroupResponse> findAll();
}
