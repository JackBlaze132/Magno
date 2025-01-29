package com.unibague.magno.application.dto.response;

import com.unibague.magno.domain.model.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String userCode;
    private boolean isExternalUser;
    private Sex sex;
    private Set<RoleResponse> roles;
}
