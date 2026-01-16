package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.ExternalUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link ExternalUserProfileResponseMapper}.
 */
@Component
@RequiredArgsConstructor
public class ExternalUserProfileResponseMapperImpl implements ExternalUserProfileResponseMapper {

    private final IUserServicePort userServicePort;
    private final UserResponseMapper userResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final ResearchSeedbedProfileResponseMapper researchSeedbedProfileResponseMapper;

    @Override
    public ExternalUserProfileResponse toResponse(ExternalUserProfile externalUserProfile) {

        Long userId = externalUserProfile.getUserId();
        UserResponse user = userResponseMapper.toResponse(userServicePort.findById(userId));

        Long academicPeriodId = externalUserProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriod = academicPeriodResponseMapper.toResponse(
                academicPeriodServicePort.findById(academicPeriodId));

        Long researchSeedbedProfileId = externalUserProfile.getResearchSeedbedProfileId();
        ResearchSeedbedProfileResponse researchSeedbedProfile = researchSeedbedProfileResponseMapper.toResponse(
                researchSeedbedProfileServicePort.findById(researchSeedbedProfileId));

        return ExternalUserProfileResponse.builder()
                .id(externalUserProfile.getId())
                .user(user)
                .academicPeriod(academicPeriod)
                .researchSeedbedProfile(researchSeedbedProfile)
                .country(externalUserProfile.getCountry())
                .typeOfExternalUser(externalUserProfile.getTypeOfExternalUser())
                .build();
    }

    @Override
    public List<ExternalUserProfileResponse> toResponseList(List<ExternalUserProfile> externalUserProfiles) {
        return externalUserProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
