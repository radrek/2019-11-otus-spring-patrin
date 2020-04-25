package ru.otus.homework.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.authentication.additional.Availabilities;
import ru.otus.homework.user.User;

@Log4j2
@RequiredArgsConstructor
@ShellComponent
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @ShellMethod(value = "Login", key = {"l", "login"})
    @ShellMethodAvailability(value = "isNotAlreadyLogin")
    public String login(@ShellOption String login) {
        log.info("User {} login", login);
        boolean success = authenticationService.login(login);
        if (success) {
            return String.format("Hello %s", login);
        } else {
            return String.format("Not found %s", login);
        }
    }

    @ShellMethod(value = "Logout", key = {"lo", "logout"})
    @ShellMethodAvailability(value = "isAlreadyLogin")
    public String logout() {
        log.info("User logout");
        authenticationService.logout();
        return "Logout is done";
    }

    @ShellMethod(value = "Create user", key = {"cu", "create-user"})
    public String createUser(@ShellOption(value = "Login") String login,
                             @ShellOption(value = "First name") String firstName,
                             @ShellOption(value = "Second name") String secondName) {
        log.info("Create user with login = {}", login);
        boolean newUser = authenticationService.createUser(login, firstName, secondName);
        if (newUser) {
            return String.format("Created user with login = %s", login);
        } else {
            return String.format("User with login = %s already created", login);
        }
    }

    public User getUser() {
        return authenticationService.getCurrentUser();
    }

    private Availability isAlreadyLogin() {
        return authenticationService.isAlreadyLogin() ? Availabilities.SUCCESS : Availabilities.NOT_LOGIN;
    }

    private Availability isNotAlreadyLogin() {
        return authenticationService.isAlreadyLogin() ? Availabilities.ALREADY_LOGIN : Availabilities.SUCCESS;
    }
}
