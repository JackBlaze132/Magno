package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFunctionaryProfileRepository extends JpaRepository<FunctionaryProfileEntity, Long> {
}
