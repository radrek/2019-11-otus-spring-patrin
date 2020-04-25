package ru.otus.homework.authentication;

import ru.otus.homework.user.User;

public interface AuthenticationController {
    String login(String login);

    String logout();

    String createUser(String login, String firstName, String secondName);

    User getUser();
}
