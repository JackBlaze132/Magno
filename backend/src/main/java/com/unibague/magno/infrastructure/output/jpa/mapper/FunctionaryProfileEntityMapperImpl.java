package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunctionaryProfileEntityMapperImpl implements FunctionaryProfileEntityMapper {

    @Override
    public FunctionaryProfile toFunctionaryProfile(FunctionaryProfileEntity functionaryProfileEntity) {
        if ( functionaryProfileEntity == null ) {
            return null;
        }

        FunctionaryProfile functionaryProfile = new FunctionaryProfile();

        functionaryProfile.setId(functionaryProfileEntity.getId());
        functionaryProfile.setUserId(functionaryProfileEntity.getUser().getId());
        functionaryProfile.setAcademicPeriodId(functionaryProfileEntity.getAcademicPeriod().getId());
        functionaryProfile.setDependencyId(functionaryProfileEntity.getDependency().getId());
        functionaryProfile.setRoleId(functionaryProfile.getRoleId());

        return functionaryProfile;
    }

    @Override
    public FunctionaryProfileEntity toFunctionaryProfileEntity(Long id, FunctionaryProfile functionaryProfile) {
        if ( id == null && functionaryProfile == null ) {
            return null;
        }

        FunctionaryProfileEntity functionaryProfileEntity = new FunctionaryProfileEntity();
        functionaryProfileEntity.setId( id );

        UserEntity userEntity = new UserEntity();
        userEntity.setId(functionaryProfile.getUserId());
        functionaryProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(functionaryProfile.getAcademicPeriodId());
        functionaryProfileEntity.setAcademicPeriod(academicPeriodEntity);

        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setId(functionaryProfile.getDependencyId());
        functionaryProfileEntity.setDependency(dependencyEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(functionaryProfile.getRoleId());
        functionaryProfileEntity.setRole(roleEntity);

        return functionaryProfileEntity;
    }

    @Override
    public FunctionaryProfileEntity toFunctionaryProfileEntity(FunctionaryProfile functionaryProfile) {
        if ( functionaryProfile == null ) {
            return null;
        }

        FunctionaryProfileEntity functionaryProfileEntity = new FunctionaryProfileEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(functionaryProfile.getUserId());
        functionaryProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(functionaryProfile.getAcademicPeriodId());
        functionaryProfileEntity.setAcademicPeriod(academicPeriodEntity);

        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setId(functionaryProfile.getDependencyId());
        functionaryProfileEntity.setDependency(dependencyEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(functionaryProfile.getRoleId());
        functionaryProfileEntity.setRole(roleEntity);

        return functionaryProfileEntity;
    }

    @Override
    public List<FunctionaryProfile> toFunctionaryProfileList(List<FunctionaryProfileEntity> functionaryProfileEntities) {

        if ( functionaryProfileEntities == null ) {
            return null;
        }

        return functionaryProfileEntities.stream()
                .map(this::toFunctionaryProfile)
                .toList();
    }
}
