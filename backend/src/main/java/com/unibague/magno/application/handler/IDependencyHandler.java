package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;

import java.util.List;

public interface IDependencyHandler {
    DependencyResponse findById(Long id);
    DependencyResponse save(DependencyRequest dependency);
    DependencyResponse updateById(Long id, DependencyRequest dependency);
    void deleteById(Long id);
    List<DependencyResponse> findAll();
}
