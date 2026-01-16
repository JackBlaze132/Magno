package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;

import java.util.List;

/**
 * Handler interface for investigation group operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for investigation group management.
 */
public interface IInvestigationGroupHandler {
    InvestigationGroupResponse findById(Long id);
    InvestigationGroupResponse save(InvestigationGroupRequest investigationGroup);
    InvestigationGroupResponse updateById(Long id, InvestigationGroupRequest investigationGroup);
    void deleteById(Long id);
    List<InvestigationGroupResponse> findAll();
}
