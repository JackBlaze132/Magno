package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;

import java.util.List;

/**
 * Handler interface for university dependency operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for faculty and department management.
 */
public interface IDependencyHandler {
    DependencyResponse findById(Long id);
    DependencyResponse save(DependencyRequest dependency);
    DependencyResponse updateById(Long id, DependencyRequest dependency);
    void deleteById(Long id);
    List<DependencyResponse> findAll();
}
