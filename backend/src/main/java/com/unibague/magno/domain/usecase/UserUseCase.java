package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.UserNotFoundException;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %s not found", id)));
    }

    @Override
    public User save(User user) {
        return userPersistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        if(userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User with id %s could not be updated because it does not exist", user.getId()));
        }
        return userPersistencePort.update(id, user);
    }

    @Override
    public void deleteById(Long id) {
        if (userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User with id %s could not be deleted because it does not exist", id));
        }
        userPersistencePort.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userPersistencePort.findAll();
    }
}
