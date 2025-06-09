package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.spi.IFunctionaryProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.FunctionaryProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IFunctionaryProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class FunctionaryProfileJpaAdapter implements IFunctionaryProfilePersistencePort {

    private final IFunctionaryProfileRepository functionaryProfileRepository;
    private final FunctionaryProfileEntityMapper functionaryProfileEntityMapper;

    @Override
    public Optional<FunctionaryProfile> findById(Long id) {
        Optional<FunctionaryProfileEntity> functionaryProfile = functionaryProfileRepository.findById(id);
        return functionaryProfile.map(functionaryProfileEntityMapper::toFunctionaryProfile);
    }

    @Override
    public FunctionaryProfile save(FunctionaryProfile functionaryProfile) {
        FunctionaryProfileEntity functionaryProfileEntity = functionaryProfileEntityMapper
                .toFunctionaryProfileEntity(functionaryProfile);
        FunctionaryProfileEntity savedFunctionaryProfileEntity = functionaryProfileRepository
                .save(functionaryProfileEntity);
        return functionaryProfileEntityMapper.toFunctionaryProfile(savedFunctionaryProfileEntity);
    }

    @Override
    public FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile) {
        FunctionaryProfileEntity functionaryProfileEntity = functionaryProfileEntityMapper
                .toFunctionaryProfileEntity(id, functionaryProfile);
        FunctionaryProfileEntity updatedFunctionaryProfileEntity = functionaryProfileRepository
                .save(functionaryProfileEntity);
        return functionaryProfileEntityMapper.toFunctionaryProfile(updatedFunctionaryProfileEntity);
    }

    @Override
    public void deleteById(Long id) {
        functionaryProfileRepository.deleteById(id);
    }

    @Override
    public List<FunctionaryProfile> findAll() {
        return functionaryProfileEntityMapper.toFunctionaryProfileList(functionaryProfileRepository.findAll());
    }

    @Override
    public List<FunctionaryProfile> findAllProfilesByUserId(Long userId) {
        return functionaryProfileEntityMapper.toFunctionaryProfileList(
                functionaryProfileRepository.findAllByUser_Id(userId));
    }

    @Override
    public boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId) {
        return functionaryProfileRepository.existsByUser_IdAndAcademicPeriod_Id(userId, academicPeriodId);
    }

}
