package ru.otus.homework.authorization;

import ru.otus.homework.user.User;

public interface AuthorizationService {
    boolean isAlreadyLogin();

    void logout();

    boolean login(String login);

    boolean createUser(String login, String firstName, String secondName);

    User getCurrentUser();
}
