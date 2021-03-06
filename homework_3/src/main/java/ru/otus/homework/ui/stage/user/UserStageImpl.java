package ru.otus.homework.ui.stage.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.user.additional.User;

@Service
public class UserStageImpl implements UserStage {
    private static final Logger LOGGER = LogManager.getLogger(UserStageImpl.class);

    private final Mediator mediator;

    public UserStageImpl(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public User getUser() {
        LOGGER.info("Get user info");
        String firstName = getPartName("user.name.first");
        String secondName = getPartName("user.name.second");
        return new User(firstName, secondName);
    }

    private String getPartName(String part) {
        return mediator.keepAskingUntilGetAnswer(part);
    }
}
