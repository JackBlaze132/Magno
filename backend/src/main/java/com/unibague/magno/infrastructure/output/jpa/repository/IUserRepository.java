package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentificationNumber(String identificationNumber) throws Exception;
    List<UserEntity> findAllByIdentificationNumber(String identificationNumber);
    List<UserEntity> findAllByIsExternalUserTrue();
    List<UserEntity> findByIsExternalUserFalse();
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.typeOfInternalUser = 'FUNCIONARIO'")
    List<UserEntity> findAllFunctionaries();

    @Query("SELECT u FROM UserEntity u WHERE u.typeOfInternalUser = 'ESTUDIANTE'")
    List<UserEntity> findAllStudents();
}
