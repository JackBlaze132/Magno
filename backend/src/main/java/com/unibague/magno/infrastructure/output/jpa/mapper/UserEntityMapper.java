package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.User;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting between {@link User} domain model and {@link UserEntity} JPA entity.
 */
public interface UserEntityMapper {

    User toUser(UserEntity userEntity);
    UserEntity toUserEntity(Long id, User user);
    UserEntity toUserEntity(User user);
    List<User> toUserList(List<UserEntity> userEntities);
}
