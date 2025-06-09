package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;
import java.util.Optional;

public interface IFunctionaryProfilePersistencePort {
    Optional<FunctionaryProfile> findById(Long id);
    FunctionaryProfile save(FunctionaryProfile functionaryProfile);
    FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile);
    void deleteById(Long id);
    List<FunctionaryProfile> findAll();

    List<FunctionaryProfile> findAllProfilesByUserId(Long userId);

    boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId);
}
