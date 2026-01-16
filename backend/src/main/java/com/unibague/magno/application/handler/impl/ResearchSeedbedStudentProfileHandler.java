package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.ResearchSeedbedStudentProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import com.unibague.magno.application.handler.interfaces.IResearchSeedbedStudentProfileHandler;
import com.unibague.magno.application.mapper.request.ResearchSeedbedStudentProfileRequestMapper;
import com.unibague.magno.application.mapper.response.ResearchSeedbedStudentProfileResponseMapper;
import com.unibague.magno.domain.api.IResearchSeedbedStudentProfileServicePort;
import com.unibague.magno.domain.exception.excel.UploadExcelException;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.infrastructure.util.excel.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IResearchSeedbedStudentProfileHandler}.
 */
@Service
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileHandler implements IResearchSeedbedStudentProfileHandler {

    private final IResearchSeedbedStudentProfileServicePort researchSeedbedStudentProfileServicePort;
    private final ResearchSeedbedStudentProfileRequestMapper researchSeedbedStudentProfileRequestMapper;
    private final ResearchSeedbedStudentProfileResponseMapper researchSeedbedStudentProfileResponseMapper;
    private final UploadService uploadService;

    @Override
    public ResearchSeedbedStudentProfileResponse findById(Long id) {
        ResearchSeedbedStudentProfile researchSeedbedStudentProfile = researchSeedbedStudentProfileServicePort.findById(id);
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfile);
    }

    @Override
    public ResearchSeedbedStudentProfileResponse save(ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfileServicePort
                .save(researchSeedbedStudentProfileRequestMapper
                        .toResearchSeedbedStudentProfile(researchSeedbedStudentProfileRequest)));
    }

    @Override
    public ResearchSeedbedStudentProfileResponse updateById(Long id, ResearchSeedbedStudentProfileRequest researchSeedbedStudentProfileRequest) {
        return researchSeedbedStudentProfileResponseMapper.toResponse(researchSeedbedStudentProfileServicePort
                .update(id, researchSeedbedStudentProfileRequestMapper
                        .toResearchSeedbedStudentProfile(researchSeedbedStudentProfileRequest)));
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedStudentProfileServicePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedStudentProfileResponse> findAll() {
        return researchSeedbedStudentProfileResponseMapper.toResponseList(researchSeedbedStudentProfileServicePort.findAll());
    }

    @Override
    public List<ResearchSeedbedStudentProfileSummaryResponse> saveAllByExcel(Long researchSeedbedProfileId, MultipartFile file) {
        List<Map<String, String>> data = getListOfMaps(file);
        List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles = researchSeedbedStudentProfileServicePort
                .saveAllByExcel(researchSeedbedProfileId, data);
        return researchSeedbedStudentProfileResponseMapper.toSummaryResponseList(researchSeedbedStudentProfiles);
    }

    @Override
    public List<ResearchSeedbedStudentProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles = researchSeedbedStudentProfileServicePort.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
        return researchSeedbedStudentProfileResponseMapper.toResponseList(researchSeedbedStudentProfiles);
    }

    private List<Map<String, String>> getListOfMaps(MultipartFile file) {
        try {
            return uploadService.uploadExcel(file);
        }
        catch (Exception e) {
            throw new UploadExcelException(
                    String.format("Error uploading the excel file with name: %s", file.getOriginalFilename())
            );
        }
    }
}
