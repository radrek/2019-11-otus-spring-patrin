package ru.otus.homework.ui.stage.greeting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.ui.interlocutor.Mediator;

public class GreetingStageImpl implements GreetingStage {
    private static final Logger LOGGER = LogManager.getLogger(GreetingStageImpl.class);

    private final Mediator mediator;

    public GreetingStageImpl(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void showGreeting() {
        LOGGER.info("Show greeting");
        mediator.say("Добро пожаловать в опросник!");
    }
}
