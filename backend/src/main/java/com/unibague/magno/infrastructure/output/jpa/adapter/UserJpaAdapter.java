package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(userEntityMapper::toUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(savedUserEntity);
    }

    @Override
    public User update(Long id, User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(id, user);
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(updatedUserEntity);
    }

    @Override
    public Optional<User> findByUserIdentification(String identification) {
        Optional<UserEntity> user = userRepository.findByIdentificationNumber(identification);
        return user.map(userEntityMapper::toUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toUserList(userRepository.findAll());
    }

    @Override
    public List<User> findAllExternalUsers() {
        return userRepository.findAllByIsExternalUserTrue()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public List<User> findAllInternalUsers() {
        return userRepository.findByIsExternalUserFalse()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser);
    }
}
