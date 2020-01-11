package ru.otus.homework.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.controller.checker.CheckerController;
import ru.otus.homework.controller.question.QuestionController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.greeting.GreetingStage;
import ru.otus.homework.ui.stage.greeting.GreetingStageImpl;
import ru.otus.homework.ui.stage.language.LanguageStage;
import ru.otus.homework.ui.stage.question.QuestionStage;
import ru.otus.homework.ui.stage.question.QuestionStageImpl;
import ru.otus.homework.ui.stage.result.ResultStage;
import ru.otus.homework.ui.stage.result.ResultStageImpl;
import ru.otus.homework.ui.stage.user.UserStage;
import ru.otus.homework.ui.stage.user.UserStageImpl;
import ru.otus.homework.ui.stage.user.additional.User;

import java.util.List;

@Service
public class UserInterfaceImpl implements UserInterface {
    private static final Logger LOGGER = LogManager.getLogger(UserInterfaceImpl.class);

    private final QuestionController questionController;
    private final CheckerController checkerController;
    private final Mediator mediator;
    private final LanguageStage languageStage;

    public UserInterfaceImpl(QuestionController questionController, CheckerController checkerController,
                             Mediator mediator, LanguageStage languageStage) {
        this.questionController = questionController;
        this.checkerController = checkerController;
        this.mediator = mediator;
        this.languageStage = languageStage;
    }

    @Override
    public void start() {
        LOGGER.info("Start user interface");
        chooseLanguage();
        showGreeting();
        User user = getUser();
        List<AnswerDto> answers = askQuestions();
        showResults(user, answers);
    }

    private void chooseLanguage() {
        LOGGER.debug("Choose language");
        languageStage.chooseLanguage();
    }

    private void showGreeting() {
        LOGGER.debug("Show greeting");
        GreetingStage greetingStage = new GreetingStageImpl(mediator);
        greetingStage.showGreeting();
    }

    private User getUser() {
        LOGGER.debug("Get user info");
        UserStage userStage = new UserStageImpl(mediator);
        return userStage.getUser();
    }
}