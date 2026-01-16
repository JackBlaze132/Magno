package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;

import java.util.List;

/**
 * Handler interface for functionary profile operations.
 * Manages profiles for university staff members who can hold roles such as
 * coordinator, tutor, or director within research groups and seedbeds.
 */
public interface IFunctionaryProfileHandler {
    FunctionaryProfileResponse findById(Long id);
    FunctionaryProfileResponse save(FunctionaryProfileRequest functionaryProfile);
    FunctionaryProfileResponse updateById(Long id, FunctionaryProfileRequest functionaryProfile);
    void deleteById(Long id);
    List<FunctionaryProfileResponse> findAll();

    /**
     * Retrieves all functionary profiles for a specific user across all periods.
     *
     * @param userId the user identifier
     * @return list of functionary profiles for the specified user
     */
    List<FunctionaryProfileResponse> findAllProfilesByUserId(Long userId);

    /**
     * Retrieves all functionary profiles for a specific academic period.
     *
     * @param academicPeriodId the academic period identifier
     * @return list of functionary profiles in the specified period
     */
    List<FunctionaryProfileResponse> findAllProfilesByAcademicPeriodId(Long academicPeriodId);
}
