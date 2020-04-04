package ru.otus.homework.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String LOGIN = "Login";

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void mustCreateUserIfNotExistsBefore() {
        boolean result = userService.createUser(LOGIN, "Ivan", "Ivanov");

        assertThat(result)
                .as("Return true, if user created and not exists before")
                .isTrue();
    }

    @Test
    void mustNotCreateUserIfUserExists() {
        userService.createUser(LOGIN, "Ivan", "Ivanov");

        boolean result = userService.createUser("Login", "Ivan", "Ivanov");

        assertThat(result)
                .as("Return false, if user already exists with same login = %s", LOGIN)
                .isFalse();
    }

    @Test
    void mustReturnUserByLogin() {
        String firstName = "Ivan";
        String secondName = "Ivanov";
        userService.createUser(LOGIN, firstName, secondName);

        User user = userService.getUser(LOGIN);

        assertThat(user)
                .as("User must not null")
                .isNotNull()
                .as("User has first name = %s", firstName)
                .hasFieldOrPropertyWithValue("firstName", firstName)
                .as("User has second name = %s", secondName)
                .hasFieldOrPropertyWithValue("secondName", secondName);

    }

    @Test
    void mustReturnNullByLoginIfUserNotExists() {
        User user = userService.getUser(LOGIN);

        assertThat(user)
                .as("User must null, because user not exists with login = %s", LOGIN)
                .isNull();

    }
}