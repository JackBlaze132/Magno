package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.TypeOfExternalUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing external user profile information.
 * External users are individuals outside the university who participate
 * in research seedbeds (e.g., visiting researchers, external advisors).
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExternalUserProfileResponse {

    private Long id;
    private UserResponse user;
    private AcademicPeriodResponse academicPeriod;
    private ResearchSeedbedProfileResponse researchSeedbedProfile;
    private String country;
    private TypeOfExternalUser typeOfExternalUser;
}
