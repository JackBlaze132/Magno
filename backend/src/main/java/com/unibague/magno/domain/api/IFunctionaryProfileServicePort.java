package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;

public interface IFunctionaryProfileServicePort {
    FunctionaryProfile findById(Long id);
    FunctionaryProfile save(FunctionaryProfile functionaryProfile);
    FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile);
    void deleteById(Long id);
    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
    List<FunctionaryProfile> findAll();
    List<FunctionaryProfile> findAllProfilesByUserId(Long userId);
    List<FunctionaryProfile> findAllProfilesByAcademicPeriodId(Long academicPeriodId);
    List<FunctionaryProfile> findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId
            (Long functionaryProfileId, Long academicPeriodId);
}
