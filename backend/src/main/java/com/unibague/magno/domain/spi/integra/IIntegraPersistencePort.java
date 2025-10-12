package com.unibague.magno.domain.spi.integra;

import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IIntegraPersistencePort {
    List<IntegraFunctionary> getAllFunctionaries();
    // This method return a list because a student can have 2 programs, so it can have 2 records in the JSON returned
    List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification);
    List<IntegraAcademicProgram> getIntegraAcademicProgramsByProgramCodes(Set<String> programCodes);
    IntegraDependency getIntegraDependencyByDependencyName(String dependencyName);

    Optional<IntegraFunctionary> getIntegraFunctionaryByEmail(String email);

    Optional<IntegraStudent> getIntegraStudentByEmail(String email);

    List<IntegraStudent> getAllStudents();

    Map<AcademicProgramType, List<IntegraAcademicProgram>> getAllAcademicProgramsMappedByType();
}
