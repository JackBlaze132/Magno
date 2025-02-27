package com.unibague.magno.domain.api.integra;

import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Map;

public interface IIntegraServicePort {
    List<IntegraFunctionary> getAllFunctionaries();
    IntegraFunctionary getIntegraFunctionaryByIdentification(String identification);
    // This method return a list because a student can have 2 programs, so it can have 2 records in the JSON returned
    List<IntegraStudent> getIntegraStudentByIdentification(String identification);
    List<IntegraAcademicProgram> getAllAcademicPrograms();
    List<IntegraDependency> getAllDependencies();
    List<String> findMissingStudentIdentificationsInIntegra(List<String> identifications);
    //This method should only be used by the User class
    IntegraStudent getFirstIntegraStudentFound(String identification);
    List<Map<String, String>> getCleanedStudentListOfMaps(List<Map<String, String>> researchSeedbedStudentProfiles);
}
