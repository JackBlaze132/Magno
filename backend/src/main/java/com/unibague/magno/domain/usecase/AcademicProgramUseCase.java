package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.exception.AcademicProgramNotFoundException;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;

import java.util.List;

public class AcademicProgramUseCase implements IAcademicProgramServicePort {

    private final IAcademicProgramPersistencePort academicProgramPersistencePort;

    public AcademicProgramUseCase(IAcademicProgramPersistencePort academicProgramPersistencePort) {
        this.academicProgramPersistencePort = academicProgramPersistencePort;
    }

    @Override
    public AcademicProgram findById(Long id) {
        return academicProgramPersistencePort.findById(id)
                .orElseThrow(() -> new AcademicProgramNotFoundException(
                        String.format("AcademicProgram with ID %d not found", id)));
    }

    @Override
    public AcademicProgram save(AcademicProgram academicProgram) {
        return academicProgramPersistencePort.save(academicProgram);
    }

    @Override
    public AcademicProgram update(Long id, AcademicProgram academicProgram) {
        if (academicProgramPersistencePort.findById(id).isEmpty()) {
            throw new AcademicProgramNotFoundException(
                    String.format("AcademicProgram with ID %d could not be updated because it does not exist", id));
        }
        return academicProgramPersistencePort.update(id, academicProgram);
    }

    @Override
    public void deleteById(Long id) {
        if (academicProgramPersistencePort.findById(id).isEmpty()) {
            throw new AcademicProgramNotFoundException(
                    String.format("AcademicProgram with ID %d could not be deleted because it does not exist", id));
        }
        academicProgramPersistencePort.deleteById(id);
    }

    @Override
    public List<AcademicProgram> findAll() {
        return academicProgramPersistencePort.findAll();
    }
}
