package ru.otus.homework.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.controller.checker.CheckerController;
import ru.otus.homework.controller.question.QuestionController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.ui.stage.greeting.GreetingStage;
import ru.otus.homework.ui.stage.greeting.GreetingStageImpl;
import ru.otus.homework.ui.stage.question.QuestionStage;
import ru.otus.homework.ui.stage.question.QuestionStageImpl;
import ru.otus.homework.ui.stage.result.ResultStage;
import ru.otus.homework.ui.stage.result.ResultStageImpl;
import ru.otus.homework.ui.stage.user.UserStage;
import ru.otus.homework.ui.stage.user.UserStageImpl;
import ru.otus.homework.ui.stage.user.additional.User;
import ru.otus.homework.ui.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

@Service
public class UserInterfaceImpl implements UserInterface {
    private static final Logger LOGGER = LogManager.getLogger(UserInterfaceImpl.class);

    private final QuestionController questionController;
    private final CheckerController checkerController;
    private final ConsoleUtils consoleUtils;
    private final Scanner in;

    public UserInterfaceImpl(QuestionController questionController, CheckerController checkerController,
                             ConsoleUtils consoleUtils) {
        this.questionController = questionController;
        this.checkerController = checkerController;
        this.consoleUtils = consoleUtils;
        this.in = new Scanner(System.in);
    }

    @Override
    public void start() {
        LOGGER.info("Start user interface");
        showGreeting();
        User user = getUser();
        List<AnswerDto> answers = askQuestions();
        showResults(user, answers);
    }

    private void showResults(User user, List<AnswerDto> answers) {
        LOGGER.debug("Show results (answers count = {})", answers.size());
        ResultStage resultStage = new ResultStageImpl(checkerController);
        resultStage.checkAnswersOnCorrect(user, answers);
    }

    private List<AnswerDto> askQuestions() {
        LOGGER.debug("Ask questions");
        QuestionStage questionStage = new QuestionStageImpl(questionController, consoleUtils, in);
        return questionStage.askQuestions();
    }

    private void showGreeting() {
        LOGGER.debug("Show greeting");
        GreetingStage greetingStage = new GreetingStageImpl();
        greetingStage.showGreeting();
    }

    private User getUser() {
        LOGGER.debug("Get user info");
        UserStage userStage = new UserStageImpl(consoleUtils, in);
        return userStage.getUser();
    }
}