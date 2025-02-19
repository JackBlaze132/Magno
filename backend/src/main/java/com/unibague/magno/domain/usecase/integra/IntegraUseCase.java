package com.unibague.magno.domain.usecase.integra;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraUserNotFoundException;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;

import java.util.List;
import java.util.Optional;

public class IntegraUseCase implements IIntegraServicePort {

    private final IIntegraPersistencePort integraPersistencePort;

    public IntegraUseCase(IIntegraPersistencePort integraPersistencePort) {
        this.integraPersistencePort = integraPersistencePort;
    }

    @Override
    public List<IntegraFunctionary> getAllFunctionaries() {
        return integraPersistencePort.getAllFunctionaries();
    }

    @Override
    public IntegraFunctionary getIntegraFunctionaryByIdentification(String identification) {

        List<IntegraFunctionary> functionaries = getAllFunctionaries();
        if(functionaries == null || functionaries.isEmpty()) {
            String message = String
                    .format("It wasn't possible to find the functionary with identification %s " +
                            "because the returned list is null or empty", identification);
            throw new IntegraUserNotFoundException(message);
        }
        Optional<IntegraFunctionary> functionaryOptional = functionaries.stream()
                .filter(f -> f.getIdentification().equals(identification))
                .findFirst();

        return functionaryOptional.orElseThrow(() -> {
            String message = String.
                    format("It wasn't possible to find the functionary with identification %s " +
                            "in the returned list", identification);
            return new IntegraUserNotFoundException(message);
        });

    }

    @Override
    public List<IntegraStudent> getIntegraStudentByIdentification(String identification) {
        return integraPersistencePort.getStudentByIdentification(identification);
    }

    @Override
    public List<IntegraAcademicProgram> getAllAcademicPrograms() {
        return integraPersistencePort.getAllAcademicPrograms();
    }

    @Override
    public List<IntegraDependency> getAllDependencies() {
        return integraPersistencePort.getAllDependencies();
    }
}
