package com.unibague.magno.domain.api;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserServicePort {
    User findById(Long id);
    User save(User user);
    User update(Long id, User user);
    Optional<User> findByUserIdentification(String identification);
    void deleteById(Long id);
    List<User> findAll();
    List<User> getUserListByListOfStudentMaps(List<Map<String, String>> cleanData);
    List<String> findAllCountries();

    // These three methods are used to get the list of users registered in this app
    List<User> findAllFunctionariesRegistered();
    List<User> findAllStudentsRegistered();
    List<User> findAllExternalUsersRegistered();

    User getUserByIntegraStudent(IntegraStudent integraStudent);
    User findUserByIdentification(List<User> users, String identification);
    User mapFromIntegraFunctionary(IntegraUserRequest userRequest);
    User mapFromIntegraStudent(IntegraUserRequest userRequest);

    List<User> findAllInternalUsersRegistered();
}
