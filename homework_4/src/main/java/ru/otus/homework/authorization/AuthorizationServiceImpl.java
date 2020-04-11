package ru.otus.homework.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.otus.homework.user.User;
import ru.otus.homework.user.UserService;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;

    private User currentUser;

    public boolean isAlreadyLogin() {
        return currentUser != null;
    }

    public void logout() {
        log.info("Current user log out");
        currentUser = null;
    }

    public boolean login(String login) {
        log.info("User {} login", login);
        currentUser = userService.getUser(login);
        return currentUser != null;
    }

    public boolean createUser(String login, String firstName, String secondName) {
        log.info("Create user with login {}", login);
        return userService.createUser(login, firstName, secondName);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
