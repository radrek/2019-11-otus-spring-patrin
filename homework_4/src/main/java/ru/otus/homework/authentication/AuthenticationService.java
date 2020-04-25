package ru.otus.homework.authentication;

import ru.otus.homework.user.User;

public interface AuthenticationService {
    boolean isAlreadyLogin();

    void logout();

    boolean login(String login);

    boolean createUser(String login, String firstName, String secondName);

    User getCurrentUser();
}
