package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.User;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

public interface UserEntityMapper {

    User toUser(UserEntity userEntity);
    UserEntity toUserEntity(Long id, User user);
    UserEntity toUserEntity(User user);
    List<User> toUserList(List<UserEntity> userEntities);
}
