package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.exception.AcademicProgramAlreadyExistsException;
import com.unibague.magno.domain.exception.AcademicProgramNotFoundException;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;

import java.util.List;
import java.util.Set;

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
        if (existsByProgramCodeAndProgramName(academicProgram.getProgramCode(), academicProgram.getName())) {
            throw new AcademicProgramAlreadyExistsException(
                    String.format("AcademicProgram with program code %s and program name %s already exists",
                            academicProgram.getProgramCode(), academicProgram.getName()));
        }
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

    @Override
    public Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids) {
        return academicProgramPersistencePort.findAcademicProgramsByIds(ids);
    }

    @Override
    public Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes) {
        return academicProgramPersistencePort.findAcademicProgramsByAcademicProgramCodes(academicProgramCodes);
    }

    @Override
    public List<AcademicProgram> saveAll() {
        return academicProgramPersistencePort.saveAll();
    }

    @Override
    public boolean existsByProgramCodeAndProgramName(String programCode, String programName) {
        return academicProgramPersistencePort.existsByProgramCodeAndProgramName(programCode, programName);
    }
}
