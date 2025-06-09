package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.spi.IFunctionaryProfilePersistencePort;

import java.util.List;

public class FunctionaryProfileUseCase implements IFunctionaryProfileServicePort {

    private final IFunctionaryProfilePersistencePort functionaryProfilePersistencePort;

    public FunctionaryProfileUseCase(IFunctionaryProfilePersistencePort functionaryProfilePersistencePort) {
        this.functionaryProfilePersistencePort = functionaryProfilePersistencePort;
    }

    @Override
    public FunctionaryProfile findById(Long id) {
        return functionaryProfilePersistencePort.findById(id)
                .orElseThrow(() -> new FunctionaryProfileNotFoundException(
                        String.format("FunctionaryProfile with ID %d not found", id)
                ));
    }

    @Override
    public FunctionaryProfile save(FunctionaryProfile functionaryProfile) {
        Long userId = functionaryProfile.getUserId();
        Long academicPeriodId = functionaryProfile.getAcademicPeriodId();
        if (functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId)) {
            throw new FunctionaryProfileAlreadyExistsException(
                    String.format("FunctionaryProfile with user ID %d and academic period ID %d already exists",
                            userId, academicPeriodId)
            );
        }
        return functionaryProfilePersistencePort.save(functionaryProfile);
    }

    @Override
    public FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile) {
        if(functionaryProfilePersistencePort.findById(id).isEmpty()) {
            throw new FunctionaryProfileNotFoundException(
                    String.format("FunctionaryProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        return functionaryProfilePersistencePort.update(id, functionaryProfile);
    }

    @Override
    public void deleteById(Long id) {
        if(functionaryProfilePersistencePort.findById(id).isEmpty()) {
            throw new FunctionaryProfileNotFoundException(
                    String.format("FunctionaryProfile with ID %d could not be deleted because it does not exist", id)
            );
        }
        functionaryProfilePersistencePort.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId) {
        return functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId);
    }

    @Override
    public List<FunctionaryProfile> findAll() {
        return functionaryProfilePersistencePort.findAll();
    }

    @Override
    public List<FunctionaryProfile> findAllProfilesByUserId(Long userId) {
        return functionaryProfilePersistencePort.findAllProfilesByUserId(userId);
    }
}
