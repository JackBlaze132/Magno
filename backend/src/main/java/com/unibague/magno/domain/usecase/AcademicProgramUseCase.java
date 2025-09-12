package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramAlreadyExistsException;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramNotFoundByCodeInExcelException;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramNotFoundException;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AcademicProgramUseCase implements IAcademicProgramServicePort {

    private final IAcademicProgramPersistencePort academicProgramPersistencePort;
    private final IIntegraServicePort integraServicePort;

    public AcademicProgramUseCase(IAcademicProgramPersistencePort academicProgramPersistencePort,
                                  IIntegraServicePort integraServicePort) {
        this.academicProgramPersistencePort = academicProgramPersistencePort;
        this.integraServicePort = integraServicePort;
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
            return findByAcademicProgramCode(academicProgram.getProgramCode());
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
        if (academicProgramCodes.isEmpty()) {
            throw new IllegalArgumentException("AcademicProgramCodes cannot be empty");
        }
        return findOrSaveAcademicPrograms(academicProgramCodes);
    }

    private Set<AcademicProgram> findOrSaveAcademicPrograms(Set<String> academicProgramCodes) {

        Set<AcademicProgram> academicPrograms = academicProgramPersistencePort
                .findAcademicProgramsByAcademicProgramCodes(academicProgramCodes);

        if (academicPrograms.isEmpty()) {

            List<IntegraAcademicProgram> integraAcademicPrograms = integraServicePort
                    .getIntegraAcademicProgramsByProgramCodes(academicProgramCodes);

            List<AcademicProgram> mappedAcademicPrograms = integraAcademicPrograms.stream()
                    .map(this::mapFromIntegraAcademicProgram)
                    .toList();

            return mappedAcademicPrograms.stream()
                    .map(this::save)
                    .collect(Collectors.toSet());
        }
        else if (academicPrograms.size() != academicProgramCodes.size()) {
            Set<AcademicProgram> missingAcademicPrograms = saveMissingAcademicProgram(academicProgramCodes, academicPrograms);
            academicPrograms.addAll(missingAcademicPrograms);
            return academicPrograms;
        }
        return academicPrograms;
    }

    private Set<AcademicProgram> saveMissingAcademicProgram
            (Set<String> academicProgramCodes, Set<AcademicProgram> academicPrograms) {
        Set<String> academicProgramCodesToFind = academicProgramCodes.stream()
                .filter(academicProgramCode -> academicPrograms.stream()
                        .noneMatch(academicProgram -> academicProgram.getProgramCode().equals(academicProgramCode)))
                .collect(Collectors.toSet());

        List<IntegraAcademicProgram> integraAcademicPrograms = integraServicePort
                .getIntegraAcademicProgramsByProgramCodes(academicProgramCodesToFind);

        List<AcademicProgram> mappedAcademicPrograms = integraAcademicPrograms.stream()
                .map(this::mapFromIntegraAcademicProgram)
                .toList();

        return mappedAcademicPrograms.stream()
                .map(this::save)
                .collect(Collectors.toSet());
    }

    private AcademicProgram mapFromIntegraAcademicProgram(IntegraAcademicProgram integraAcademicProgram) {
        AcademicProgram academicProgram = new AcademicProgram();
        academicProgram.setName(integraAcademicProgram.getProgramName());
        academicProgram.setProgramCode(integraAcademicProgram.getProgramCode());
        academicProgram.setType(AcademicProgramType.PREGRADO);
        return academicProgram;
    }

    @Override
    public boolean existsByProgramCodeAndProgramName(String programCode, String programName) {
        return academicProgramPersistencePort.existsByProgramCodeAndProgramName(programCode, programName);
    }

    @Override
    public AcademicProgram findByAcademicProgramCode(String academicProgramCode) {
        return academicProgramPersistencePort.findByAcademicProgramCode(academicProgramCode);
    }
}
