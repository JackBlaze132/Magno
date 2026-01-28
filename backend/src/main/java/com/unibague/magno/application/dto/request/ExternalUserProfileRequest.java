package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.TypeOfExternalUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating an external user profile.
 * External users are individuals outside the university who participate
 * in research seedbeds (e.g., visiting researchers, external advisors).
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExternalUserProfileRequest {

    @NotNull(message = "El campo 'user_id' es obligatorio")
    @Positive(message = "El campo 'user_id' debe ser un número positivo")
    private Long userId;

    @NotNull(message = "El campo 'academic_period_id' es obligatorio")
    @Positive(message = "El campo 'academic_period_id' debe ser un número positivo")
    private Long academicPeriodId;

    @NotNull(message = "El campo 'research_seedbed_profile_id' es obligatorio")
    @Positive(message = "El campo 'research_seedbed_profile_id' debe ser un número positivo")
    private Long researchSeedbedProfileId;

    @NotBlank(message = "El campo 'country' es obligatorio")
    private String country;

    @NotBlank(message = "El campo 'organization_name' es obligatorio")
    private String organizationName;

    @NotNull(message = "El campo 'type_of_external_user' es obligatorio")
    private TypeOfExternalUser typeOfExternalUser;
}
