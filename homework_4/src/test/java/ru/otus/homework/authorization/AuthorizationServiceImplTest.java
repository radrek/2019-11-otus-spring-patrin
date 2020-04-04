package ru.otus.homework.authorization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.user.User;
import ru.otus.homework.user.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    private static final String LOGIN = "login";

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private UserService userService;

    @Test
    void mustReturnTrueIfUserLogin() {
        when(userService.getUser(LOGIN)).thenReturn(new User(null, null));
        authorizationService.login(LOGIN);

        boolean result = authorizationService.isAlreadyLogin();

        assertThat(result)
                .as("If user login now, return true")
                .isTrue();
    }

    @Test
    void mustReturnFalseIfUserNotLoginNow() {
        boolean result = authorizationService.isAlreadyLogin();

        assertThat(result)
                .as("If user not login now, return false")
                .isFalse();
    }

    @Test
    void isSuccessLoginIfUserExists() {
        when(userService.getUser(LOGIN)).thenReturn(new User(null, null));

        boolean result = authorizationService.login(LOGIN);

        assertThat(result)
                .as("If user exists, return true")
                .isTrue();
    }

    @Test
    void isFailedLoginIfUserNotExists() {
        when(userService.getUser(LOGIN)).thenReturn(null);

        boolean result = authorizationService.login(LOGIN);

        assertThat(result)
                .as("If user not exists, return false")
                .isFalse();
    }

    @Test
    void mustCreateUserIfNotExistBefore() {
        String firstName = "Ivan";
        String secondName = "Ivanov";
        when(userService.createUser(LOGIN, firstName, secondName)).thenReturn(true);

        boolean result = authorizationService.createUser(LOGIN, firstName, secondName);

        assertThat(result)
                .as("Return true, if user created")
                .isTrue();
    }

    @Test
    void mustNotCreateUserIfAlreadyExists() {
        String firstName = "Ivan";
        String secondName = "Ivanov";
        when(userService.createUser(LOGIN, firstName, secondName)).thenReturn(false);

        boolean result = authorizationService.createUser(LOGIN, firstName, secondName);

        assertThat(result)
                .as("Return false, if user already exists")
                .isFalse();
    }
}