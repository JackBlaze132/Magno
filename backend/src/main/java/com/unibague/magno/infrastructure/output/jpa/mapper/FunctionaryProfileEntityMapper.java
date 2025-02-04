package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import org.mapstruct.Mapping;

import java.util.List;

public interface FunctionaryProfileEntityMapper {

    FunctionaryProfile toFunctionaryProfile(FunctionaryProfileEntity functionaryProfileEntity);

    @Mapping(source = "id", target = "id")
    FunctionaryProfileEntity toFunctionaryProfileEntity(Long id, FunctionaryProfile functionaryProfile);

    FunctionaryProfileEntity toFunctionaryProfileEntity(FunctionaryProfile functionaryProfile);
    List<FunctionaryProfile> toFunctionaryProfileList(List<FunctionaryProfileEntity> functionaryProfileEntities);
}
