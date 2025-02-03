package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;
import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvestigationGroupProfileResponseMapperImpl implements InvestigationGroupProfileResponseMapper{

    private final IInvestigationGroupServicePort investigationGroupServicePort;
    private final InvestigationGroupResponseMapper investigationGroupResponseMapper;

    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final FunctionaryProfileResponseMapper functionaryProfileResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    @Override
    public InvestigationGroupProfileResponse toResponse(InvestigationGroupProfile investigationGroupProfile) {

        Long investigationGroupId = investigationGroupProfile.getInvestigationGroupId();
        InvestigationGroupResponse investigationGroupResponse = investigationGroupResponseMapper
                .toResponse(investigationGroupServicePort
                .findById(investigationGroupId));

        Long coordinatorId = investigationGroupProfile.getCoordinatorId();
        FunctionaryProfileResponse coordinator = functionaryProfileResponseMapper
                .toResponse(functionaryProfileServicePort
                .findById(coordinatorId));

        Long academicPeriodId = investigationGroupProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriodResponse = academicPeriodResponseMapper
                .toResponse(academicPeriodServicePort
                .findById(academicPeriodId));

        return InvestigationGroupProfileResponse.builder()
                .id(investigationGroupProfile.getId())
                .investigationGroup(investigationGroupResponse)
                .coordinator(coordinator)
                .academicPeriod(academicPeriodResponse)
                .build();
    }

    @Override
    public List<InvestigationGroupProfileResponse> toResponseList(List<InvestigationGroupProfile> investigationGroupProfiles) {
        return investigationGroupProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
