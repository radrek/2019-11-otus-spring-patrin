package ru.otus.homework.ui.stage.greeting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GreetingStageImpl implements GreetingStage {
    private static final Logger LOGGER = LogManager.getLogger(GreetingStageImpl.class);

    @Override
    public void showGreeting() {
        LOGGER.info("Show greeting");
        System.out.println("Добро пожаловать в опросник!");
    }
}
