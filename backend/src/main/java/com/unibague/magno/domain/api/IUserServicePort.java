package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {
    User findById(Long id);
    User save(User user);
    User update(Long id, User user);
    Optional<User> findByUserIdentification(String identification);
    void deleteById(Long id);
    List<User> findAll();
    List<String> findAllCountries();
}
