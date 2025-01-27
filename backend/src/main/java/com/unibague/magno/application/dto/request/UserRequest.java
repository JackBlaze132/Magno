package com.unibague.magno.application.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.unibague.magno.domain.model.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {

    private String fullName;
    private String identificationNumber;
    private String email;
    private String userCode;
    private boolean isExternalUser;
    Sex sex;
    private Set<Long> roleIds;
}
