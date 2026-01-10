package com.unibague.magno.application.mapper.request.impl;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.mapper.request.AcademicPeriodRequestMapper;
import com.unibague.magno.domain.model.AcademicPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcademicPeriodRequestMapperImpl implements AcademicPeriodRequestMapper {

    @Override
    public AcademicPeriod toAcademicPeriod(AcademicPeriodRequest academicPeriodRequest) {

        if (academicPeriodRequest == null) {
            return null;
        }

        AcademicPeriod academicPeriod = new AcademicPeriod(
                null,
                academicPeriodRequest.getName(),
                academicPeriodRequest.getStartDate(),
                academicPeriodRequest.getEndDate(),
                academicPeriodRequest.getIsCurrent(),
                true
        );

        return academicPeriod;
    }
}

