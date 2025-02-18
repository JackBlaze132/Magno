package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.TypeOfExternalUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExternalUserProfileRequest {

    @NotNull(message = "user_id field is required")
    @Positive(message = "user_id field must be positive")
    private Long userId;

    @NotNull(message = "academic_period_id field is required")
    @Positive(message = "academic_period_id field must be positive")
    private Long academicPeriodId;

    @NotNull(message = "research_seedbed_profile_id field is required")
    @Positive(message = "research_seedbed_profile_id field must be positive")
    private Long researchSeedbedProfileId;

    @NotBlank(message = "country field is required")
    private String country;

    @NotNull(message = "type_of_external_user field is required")
    private TypeOfExternalUser typeOfExternalUser;
}
