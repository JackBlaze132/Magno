package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;
import com.unibague.magno.application.handler.interfaces.IInvestigationGroupHandler;
import com.unibague.magno.application.mapper.request.InvestigationGroupRequestMapper;
import com.unibague.magno.application.mapper.response.InvestigationGroupResponseMapper;
import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.model.InvestigationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestigationGroupHandler implements IInvestigationGroupHandler {

    private final IInvestigationGroupServicePort investigationGroupServicePort;
    private final InvestigationGroupRequestMapper investigationGroupRequestMapper;
    private final InvestigationGroupResponseMapper investigationGroupResponseMapper;

    @Override
    public InvestigationGroupResponse findById(Long id) {
        InvestigationGroup investigationGroup = investigationGroupServicePort.findById(id);
        return investigationGroupResponseMapper.toResponse(investigationGroup);
    }

    @Override
    public InvestigationGroupResponse save(InvestigationGroupRequest investigationGroup) {
        return investigationGroupResponseMapper.toResponse(investigationGroupServicePort
                .save(investigationGroupRequestMapper.toInvestigationGroup(investigationGroup)));
    }

    @Override
    public InvestigationGroupResponse updateById(Long id, InvestigationGroupRequest investigationGroup) {
        return investigationGroupResponseMapper.toResponse(investigationGroupServicePort
                .update(id, investigationGroupRequestMapper.toInvestigationGroup(investigationGroup)));
    }

    @Override
    public void deleteById(Long id) {
        investigationGroupServicePort.deleteById(id);
    }

    @Override
    public List<InvestigationGroupResponse> findAll() {
        return investigationGroupResponseMapper.toResponseList(investigationGroupServicePort.findAll());
    }
}
