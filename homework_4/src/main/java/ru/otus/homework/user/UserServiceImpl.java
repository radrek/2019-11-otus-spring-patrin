package ru.otus.homework.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final Map<String, User> users = new HashMap<>();

    public User getUser(String login) {
        log.info("Get user by login {}", login);
        return users.get(login);
    }

    public boolean createUser(String login, String firstName, String secondName) {
        log.info("Create user with login = {}", login);
        if (users.containsKey(login)) {
            return false;
        } else {
            User user = new User(firstName, secondName);
            users.put(login, user);
            return true;
        }
    }
}
