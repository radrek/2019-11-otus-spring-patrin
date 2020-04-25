package ru.otus.homework.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.homework.user.User;
import ru.otus.homework.user.UserService;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthenticationControllerImplTest {

    private static final String LOGIN = "user";

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "users", new HashMap<>());
    }

    @Test
    void mustLoginFailed() {
        String resultMessage = authenticationController.login(LOGIN);

        assertThat(resultMessage)
                .as("Login is failed, because user not created")
                .isEqualTo("Not found " + LOGIN);
    }

    @Test
    void mustLoginSuccess() {
        authenticationController.createUser(LOGIN, "Ivan", "Ivanov");
        String resultMessage = authenticationController.login(LOGIN);

        assertThat(resultMessage)
                .as("Login is success, because user login")
                .isEqualTo("Hello " + LOGIN);
    }

    @Test
    void isLogoutSuccess() {
        String resultMessage = authenticationController.logout();

        assertThat(resultMessage)
                .as("Logout is success")
                .isEqualTo("Logout is done");
    }

    @Test
    void mustCreateUser() {
        String resultMessage = authenticationController.createUser(LOGIN, "Ivan", "Ivanov");

        assertThat(resultMessage)
                .as("Created user with login = %s", LOGIN)
                .isEqualTo("Created user with login = " + LOGIN);
    }

    @Test
    void mustNotCreateDuplicateUserWithSameLogin() {
        authenticationController.createUser(LOGIN, "Ivan", "Ivanov");
        String resultMessage = authenticationController.createUser(LOGIN, "Ivan", "Ivanov");

        assertThat(resultMessage)
                .as("Not created duplicate user with same login = %s", LOGIN)
                .isEqualTo(String.format("User with login = %s already created", LOGIN));
    }

    @Test
    void mustReturnNullIfUserNotLogin() {
        User user = authenticationController.getUser();

        assertThat(user)
                .as("user must be null, if not login")
                .isNull();
    }

    @Test
    void mustReturnCurrentUser() {
        String firstName = "Ivan";
        String secondName = "Ivanov";
        authenticationController.createUser(LOGIN, firstName, secondName);
        authenticationController.login(LOGIN);

        User user = authenticationController.getUser();

        assertThat(user)
                .as("User must be not null if already login")
                .isNotNull()
                .as("First name is '%s'", firstName)
                .hasFieldOrPropertyWithValue("firstName", firstName)
                .as("Second name is '%s'", secondName)
                .hasFieldOrPropertyWithValue("secondName", secondName);
    }
}
