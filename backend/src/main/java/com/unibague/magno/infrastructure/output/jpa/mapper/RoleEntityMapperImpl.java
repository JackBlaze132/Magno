package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleEntityMapperImpl implements RoleEntityMapper{

    @Override
    public Role toRole(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleEntity.getId() );
        role.setName( roleEntity.getName() );
        role.setUsers( userEntitySetToUserSet( roleEntity.getUsers() ) );

        return role;
    }


    @Override
    public RoleEntity toRoleEntity(Long id, Role role) {
        if ( id == null && role == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        if ( role != null ) {
            roleEntity.setName( role.getName() );
            roleEntity.setUsers( userSetToUserEntitySet( role.getUsers() ) );
        }
        roleEntity.setId( id );

        return roleEntity;
    }

    @Override
    public RoleEntity toRoleEntity(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( role.getId() );
        roleEntity.setName( role.getName() );
        roleEntity.setUsers( userSetToUserEntitySet( role.getUsers() ) );

        return roleEntity;
    }

    @Override
    public List<Role> toRoleList(List<RoleEntity> roleEntities) {
        if ( roleEntities == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( roleEntities.size() );
        for ( RoleEntity roleEntity : roleEntities ) {
            list.add( toRole( roleEntity ) );
        }

        return list;
    }

    private Set<User> userEntitySetToUserSet(Set<UserEntity> userEntities) {

        if(userEntities == null) {
            return null;
        }
        return userEntities.stream()
                .map(userEntity ->{
                    User user = new User();
                    user.setId(userEntity.getId());
                    user.setFullName(userEntity.getFullName());
                    user.setIdentificationNumber(userEntity.getIdentificationNumber());
                    user.setEmail(userEntity.getEmail());
                    user.setUserCode(userEntity.getUserCode());
                    user.setExternalUser(userEntity.isExternalUser());
                    user.setSex(userEntity.getSex());
                    return user;
                })
                .collect(Collectors.toSet());
    }

    private Set<UserEntity> userSetToUserEntitySet(Set<User> users) {

        if(users == null) {
            return null;
        }

        return users.stream()
                .map(user -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setId(user.getId());
                    userEntity.setFullName(user.getFullName());
                    userEntity.setIdentificationNumber(user.getIdentificationNumber());
                    userEntity.setEmail(user.getEmail());
                    userEntity.setUserCode(user.getUserCode());
                    userEntity.setExternalUser(user.isExternalUser());
                    userEntity.setSex(user.getSex());
                    return userEntity;
                })
                .collect(Collectors.toSet());
    }

}
