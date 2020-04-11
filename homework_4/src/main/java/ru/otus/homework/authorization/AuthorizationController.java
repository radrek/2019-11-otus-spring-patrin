package ru.otus.homework.authorization;

import ru.otus.homework.user.User;

public interface AuthorizationController {
    String login(String login);

    String logout();

    String createUser(String login, String firstName, String secondName);

    User getUser();
}
