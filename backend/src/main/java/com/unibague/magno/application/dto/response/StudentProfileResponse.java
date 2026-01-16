package com.unibague.magno.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response DTO containing student profile information.
 * Includes the student's user data, academic period, semester,
 * enrolled academic programs, and assigned role.
 */
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentProfileResponse {

    private Long id;
    private UserResponse user;
    private AcademicPeriodResponse academicPeriod;
    private Byte semester;
    private Set<AcademicProgramResponse> academicPrograms;
    private RoleResponse role;
}
