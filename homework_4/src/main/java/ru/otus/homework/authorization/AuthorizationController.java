package ru.otus.homework.authorization;

public interface AuthorizationController {
    String login(String login);

    String logout();

    String createUser(String login, String firstName, String secondName);
}
