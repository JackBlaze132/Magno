package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExternalUserProfileRepository extends JpaRepository<ExternalUserProfileEntity, Long> {
}
