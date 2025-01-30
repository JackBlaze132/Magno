package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;

public interface IFunctionaryProfileServicePort {
    FunctionaryProfile findById(Long id);
    FunctionaryProfile save(FunctionaryProfile functionaryProfile);
    FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile);
    void deleteById(Long id);
    List<FunctionaryProfile> findAll();
}
