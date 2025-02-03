package com.unibague.magno.domain.api.integra;

import com.unibague.magno.domain.model.integra.IntegraFunctionary;

import java.util.List;

public interface IIntegraServicePort {
    List<IntegraFunctionary> getAllFunctionaries();
    IntegraFunctionary getIntegraFunctionaryByIdentification(String identification);
}
