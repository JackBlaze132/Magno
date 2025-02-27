package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.application.handler.interfaces.IDependencyHandler;
import com.unibague.magno.application.mapper.request.DependencyRequestMapper;
import com.unibague.magno.application.mapper.response.DependencyResponseMapper;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.model.Dependency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DependencyHandler implements IDependencyHandler {

    private final IDependencyServicePort dependencyServicePort;
    private final DependencyRequestMapper dependencyRequestMapper;
    private final DependencyResponseMapper dependencyResponseMapper;

    @Override
    public DependencyResponse findById(Long id) {
        Dependency dependency = dependencyServicePort.findById(id);
        return dependencyResponseMapper.toResponse(dependency);
    }

    @Override
    public DependencyResponse save(DependencyRequest dependency) {
        return dependencyResponseMapper.toResponse(dependencyServicePort
                .save(dependencyRequestMapper.toDependency(dependency)));
    }

    @Override
    public DependencyResponse updateById(Long id, DependencyRequest dependency) {
        return dependencyResponseMapper.toResponse(dependencyServicePort
                .update(id, dependencyRequestMapper.toDependency(dependency)));
    }

    @Override
    public void deleteById(Long id) {
        dependencyServicePort.deleteById(id);
    }

    @Override
    public List<DependencyResponse> findAll() {
        return dependencyResponseMapper.toResponseList(dependencyServicePort.findAll());
    }

    @Override
    public List<DependencyResponse> saveAllFromIntegra() {
        return dependencyResponseMapper.toResponseList(dependencyServicePort.saveAllFromIntegra());
    }
}
