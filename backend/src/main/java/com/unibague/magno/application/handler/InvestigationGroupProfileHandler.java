package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.application.mapper.request.InvestigationGroupProfileRequestMapper;
import com.unibague.magno.application.mapper.response.InvestigationGroupProfileResponseMapper;
import com.unibague.magno.domain.api.IInvestigationGroupProfileServicePort;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestigationGroupProfileHandler implements IInvestigationGroupProfileHandler{

    private final IInvestigationGroupProfileServicePort investigationGroupProfileServicePort;
    private final InvestigationGroupProfileRequestMapper investigationGroupProfileRequestMapper;
    private final InvestigationGroupProfileResponseMapper investigationGroupProfileResponseMapper;

    @Override
    public InvestigationGroupProfileResponse findById(Long id) {
        InvestigationGroupProfile investigationGroupProfile = investigationGroupProfileServicePort.findById(id);
        return investigationGroupProfileResponseMapper.toResponse(investigationGroupProfile);
    }

    @Override
    public InvestigationGroupProfileResponse save(InvestigationGroupProfileRequest investigationGroupProfileRequest) {
        return investigationGroupProfileResponseMapper.toResponse(investigationGroupProfileServicePort
                .save(investigationGroupProfileRequestMapper
                        .toInvestigationGroupProfile(investigationGroupProfileRequest)));
    }

    @Override
    public InvestigationGroupProfileResponse updateById(Long id, InvestigationGroupProfileRequest investigationGroupProfileRequest) {
        return investigationGroupProfileResponseMapper.toResponse(investigationGroupProfileServicePort
                .update(id, investigationGroupProfileRequestMapper
                        .toInvestigationGroupProfile(investigationGroupProfileRequest)));
    }

    @Override
    public void deleteById(Long id) {
        investigationGroupProfileServicePort.deleteById(id);
    }

    @Override
    public List<InvestigationGroupProfileResponse> findAll() {
        return investigationGroupProfileResponseMapper.toResponseList(investigationGroupProfileServicePort.findAll());
    }
}
