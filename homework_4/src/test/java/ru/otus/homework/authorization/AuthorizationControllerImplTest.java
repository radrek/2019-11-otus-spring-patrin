package ru.otus.homework.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.homework.user.UserService;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthorizationControllerImplTest {

    private static final String LOGIN = "user";

    @Autowired
    private AuthorizationController authorizationController;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "users", new HashMap<>());
    }

    @Test
    void mustLoginFailed() {
        String resultMessage = authorizationController.login(LOGIN);

        assertThat(resultMessage)
                .as("Login is failed, because user not created")
                .isEqualTo("Not found " + LOGIN);
    }

    @Test
    void mustLoginSuccess() {
        authorizationController.createUser(LOGIN, "Ivan", "Ivanov");
        String resultMessage = authorizationController.login(LOGIN);

        assertThat(resultMessage)
                .as("Login is success, because user login")
                .isEqualTo("Hello " + LOGIN);
    }

    @Test
    void isLogoutSuccess() {
        String resultMessage = authorizationController.logout();

        assertThat(resultMessage)
                .as("Logout is success")
                .isEqualTo("Logout is done");
    }

    @Test
    void mustCreateUser() {
        String resultMessage = authorizationController.createUser(LOGIN, "Ivan", "Ivanov");

        assertThat(resultMessage)
                .as("Created user with login = %s", LOGIN)
                .isEqualTo("Created user with login = " + LOGIN);
    }

    @Test
    void mustNotCreateDuplicateUserWithSameLogin() {
        authorizationController.createUser(LOGIN, "Ivan", "Ivanov");
        String resultMessage = authorizationController.createUser(LOGIN, "Ivan", "Ivanov");

        assertThat(resultMessage)
                .as("Not created duplicate user with same login = %s", LOGIN)
                .isEqualTo(String.format("User with login = %s already created", LOGIN));
    }
}