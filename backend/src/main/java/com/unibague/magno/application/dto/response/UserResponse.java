package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO containing user information.
 * Includes personal data such as identification, name, contact details,
 * and the user's internal type classification.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String userCode;

    @JsonProperty("is_external_user")
    private boolean isExternalUser;

    private Sex sex;
    private TypeOfInternalUser typeOfInternalUser;
}
