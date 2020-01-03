package ru.otus.homework.ui.stage.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.user.additional.User;

public class UserStageImpl implements UserStage {
    private static final Logger LOGGER = LogManager.getLogger(UserStageImpl.class);

    private final Mediator mediator;

    public UserStageImpl(Mediator mediator) {
        this.mediator = mediator;
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
        return mediator.KeepAskingUntilGetAnswer(partNameQuestion);
    }
}
