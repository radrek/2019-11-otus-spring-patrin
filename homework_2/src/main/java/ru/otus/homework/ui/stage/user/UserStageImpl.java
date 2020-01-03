package ru.otus.homework.ui.stage.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.ui.stage.user.additional.User;
import ru.otus.homework.ui.util.ConsoleUtils;

import java.util.Scanner;

public class UserStageImpl implements UserStage {
    private static final Logger LOGGER = LogManager.getLogger(UserStageImpl.class);

    private final ConsoleUtils consoleUtils;
    private final Scanner in;

    public UserStageImpl(ConsoleUtils consoleUtils, Scanner in) {
        this.consoleUtils = consoleUtils;
        this.in = in;
    }


    @Override
    public User getUser() {
        LOGGER.info("Get user info");
        String firstName = getPartName("Имя");
        String secondName = getPartName("Фамилию");
        return new User(firstName, secondName);
    }

    private String getPartName(String part) {
        String partNameQuestion = String.format("Напишите %s: ", part);
        return consoleUtils.askUserAndGetNotBlankAnswer(partNameQuestion, in);
    }
}
