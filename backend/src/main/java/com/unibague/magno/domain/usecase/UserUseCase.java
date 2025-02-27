package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IUserPersistencePort;

import java.util.*;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IIntegraServicePort integraServicePort;

    public UserUseCase(IUserPersistencePort userPersistencePort,
                       IIntegraServicePort integraServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.integraServicePort = integraServicePort;
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
    public Optional<User> findByUserIdentification(String identification) {
        return userPersistencePort.findByUserIdentification(identification);
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

    @Override
    // Notice that this method suppose that the field with the identifications is called "identification"
    // Also, if some
    public List<User> getUserListByListOfStudentMaps(List<Map<String, String>> cleanedStudentListOfMaps) {
        return cleanedStudentListOfMaps.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get(IDENTIFICATION); // Getting the identification from the Map
                    return findByUserIdentification(identification)
                            .orElseGet(() -> {
                                // If the user doesn't exist, we create it
                                IntegraStudent integraStudent = integraServicePort.
                                        getFirstIntegraStudentFound(identification);
                                User user = getUserByIntegraStudent(integraStudent);
                                return save(user);
                            });
                })
                .toList();
    }

    @Override
    public List<String> findAllCountries() {
        String[] countryCodes = Locale.getISOCountries();
        Locale spanishLocale = Locale.of("es");
        return Arrays.stream(countryCodes)
                .map(countryCode -> Locale.of("", countryCode))
                .map(countryLocale -> countryLocale.getDisplayCountry(spanishLocale))
                .toList();
    }

    @Override
    public User getUserByIntegraStudent(IntegraStudent integraStudent) {
        User user = new User();
        user.setIdentificationNumber(integraStudent.getIdentification());
        user.setFullName(integraStudent.getName());
        user.setEmail(integraStudent.getEmail());
        user.setUserCode(integraStudent.getCodeStudent());
        user.setExternalUser(false);
        user.setSex(integraStudent.getSexo().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO);
        user.setRoleIds(Set.of(1L));
        return user;
    }

    @Override
    public User findUserByIdentification(List<User> users, String identification) {
        return users.stream()
                .filter(u -> u.getIdentificationNumber().equals(identification))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with identification %s not found", identification)
                ));
    }
}
