package com.unibague.magno.domain.spi.integra;

import com.unibague.magno.domain.model.integra.IntegraFunctionary;

import java.util.List;

public interface IIntegraPersistencePort {
    List<IntegraFunctionary> getAllFunctionaries();
}
