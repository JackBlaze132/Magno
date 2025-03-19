package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.User;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {
    @Override
    public User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();

        user.setId(userEntity.getId());
        user.setFullName(userEntity.getFullName());
        user.setIdentificationNumber(userEntity.getIdentificationNumber());
        user.setEmail(userEntity.getEmail());
        user.setUserCode(userEntity.getUserCode());
        user.setExternalUser(userEntity.isExternalUser());
        user.setSex(userEntity.getSex());

        return user;
    }

    @Override
    public UserEntity toUserEntity(Long id, User user) {
        if (id == null && user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();


        userEntity.setFullName(user.getFullName());
        userEntity.setIdentificationNumber(user.getIdentificationNumber());
        userEntity.setEmail(user.getEmail());
        userEntity.setUserCode(user.getUserCode());
        userEntity.setExternalUser(user.isExternalUser());
        userEntity.setSex(user.getSex());

        userEntity.setId(id);

        return userEntity;
    }

    @Override
    public UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setFullName(user.getFullName());
        userEntity.setIdentificationNumber(user.getIdentificationNumber());
        userEntity.setEmail(user.getEmail());
        userEntity.setUserCode(user.getUserCode());
        userEntity.setExternalUser(user.isExternalUser());
        userEntity.setSex(user.getSex());

        return userEntity;
    }

    @Override
    public List<User> toUserList(List<UserEntity> userEntities) {
        if (userEntities == null) {
            return null;
        }

        return userEntities.stream()
                .map(this::toUser)
                .toList();
    }

}
