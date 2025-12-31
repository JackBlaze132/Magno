package com.unibague.magno.domain.usecase.helper;

public interface IUserHelper {
    void addDiriUser(String diriIdentification, Long diriUserId);
    void deleteDiriUser(String diriIdentification, Long diriUserId);
}
