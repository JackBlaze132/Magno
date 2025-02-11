package com.unibague.magno.domain.spi.integra;

import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;

public interface IIntegraPersistencePort {
    List<IntegraFunctionary> getAllFunctionaries();
    // This method return a list because a student can have 2 programs, so it can have 2 records in the JSON returned
    List<IntegraStudent> getStudentByIdentification(String identification);
}
