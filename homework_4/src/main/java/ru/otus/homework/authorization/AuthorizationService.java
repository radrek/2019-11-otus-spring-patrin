package ru.otus.homework.authorization;

public interface AuthorizationService {
    boolean isAlreadyLogin();

    void logout();

    boolean login(String login);

    boolean createUser(String login, String firstName, String secondName);
}
