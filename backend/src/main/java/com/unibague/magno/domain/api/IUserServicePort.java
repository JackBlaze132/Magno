package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.User;

import java.util.List;

public interface IUserServicePort {
    User findById(Long id);
    User save(User user);
    User update(Long id, User user);
    void deleteById(Long id);
    List<User> findAll();
}
