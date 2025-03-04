package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.AcademicProgramRequest;
import com.unibague.magno.application.dto.response.AcademicProgramResponse;
import com.unibague.magno.application.handler.interfaces.IAcademicProgramHandler;
import com.unibague.magno.application.mapper.request.AcademicProgramRequestMapper;
import com.unibague.magno.application.mapper.response.AcademicProgramResponseMapper;
import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.model.AcademicProgram;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicProgramHandler implements IAcademicProgramHandler {

    private final IAcademicProgramServicePort academicProgramServicePort;
    private final AcademicProgramRequestMapper academicProgramRequestMapper;
    private final AcademicProgramResponseMapper academicProgramResponseMapper;

    @Override
    public AcademicProgramResponse findById(Long id) {
        AcademicProgram academicProgram = academicProgramServicePort.findById(id);
        return academicProgramResponseMapper.toResponse(academicProgram);
    }

    @Override
    public AcademicProgramResponse save(AcademicProgramRequest academicProgram) {
        return academicProgramResponseMapper.toResponse(academicProgramServicePort
                .save(academicProgramRequestMapper.toAcademicProgram(academicProgram)));
    }

    @Override
    public AcademicProgramResponse updateById(Long id, AcademicProgramRequest academicProgram) {
        return academicProgramResponseMapper.toResponse(academicProgramServicePort
                .update(id, academicProgramRequestMapper.toAcademicProgram(academicProgram)));
    }

    @Override
    public void deleteById(Long id) {
        academicProgramServicePort.deleteById(id);
    }

    @Override
    public List<AcademicProgramResponse> findAll() {
        return academicProgramResponseMapper.toResponseList(academicProgramServicePort.findAll());
    }
}
