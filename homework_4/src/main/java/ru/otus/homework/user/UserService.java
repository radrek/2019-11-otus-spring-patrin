package ru.otus.homework.user;

public interface UserService {

    User getUser(String login);

    boolean createUser(String login, String firstName, String secondName);
}
