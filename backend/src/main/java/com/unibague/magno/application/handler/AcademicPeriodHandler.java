package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.mapper.request.AcademicPeriodRequestMapper;
import com.unibague.magno.application.mapper.response.AcademicPeriodResponseMapper;
import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.model.AcademicPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicPeriodHandler implements IAcademicPeriodHandler{

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodRequestMapper academicPeriodRequestMapper;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    @Override
    public AcademicPeriodResponse findById(Long id) {
        AcademicPeriod academicPeriod = academicPeriodServicePort.findById(id);
        return academicPeriodResponseMapper.toResponse(academicPeriod);
    }

    @Override
    public AcademicPeriodResponse save(AcademicPeriodRequest academicPeriod) {
        return academicPeriodResponseMapper.toResponse(academicPeriodServicePort
                .save(academicPeriodRequestMapper.toAcademicPeriod(academicPeriod)));
    }

    @Override
    public AcademicPeriodResponse updateById(Long id, AcademicPeriodRequest academicPeriod) {
        return academicPeriodResponseMapper.toResponse(academicPeriodServicePort
                .update(id, academicPeriodRequestMapper.toAcademicPeriod(academicPeriod)));
    }

    @Override
    public void deleteById(Long id) {
        academicPeriodServicePort.deleteById(id);
    }

    @Override
    public List<AcademicPeriodResponse> findAll() {
        return academicPeriodResponseMapper.toResponseList(academicPeriodServicePort.findAll());
    }
}
