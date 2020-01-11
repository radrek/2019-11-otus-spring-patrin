package ru.otus.homework.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.ui.stage.greeting.GreetingStage;
import ru.otus.homework.ui.stage.language.LanguageStage;
import ru.otus.homework.ui.stage.question.QuestionStage;
import ru.otus.homework.ui.stage.result.ResultStage;
import ru.otus.homework.ui.stage.user.UserStage;
import ru.otus.homework.ui.stage.user.additional.User;

import java.util.List;

@Service
public class UserInterfaceImpl implements UserInterface {
    private static final Logger LOGGER = LogManager.getLogger(UserInterfaceImpl.class);

    private final LanguageStage languageStage;
    private final GreetingStage greetingStage;
    private final UserStage userStage;
    private final QuestionStage questionStage;
    private final ResultStage resultStage;

    public UserInterfaceImpl(LanguageStage languageStage,
                             GreetingStage greetingStage,
                             UserStage userStage,
                             QuestionStage questionStage,
                             ResultStage resultStage) {
        this.languageStage = languageStage;
        this.greetingStage = greetingStage;
        this.userStage = userStage;
        this.questionStage = questionStage;
        this.resultStage = resultStage;
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
        greetingStage.showGreeting();
    }

    private User getUser() {
        LOGGER.debug("Get user info");
        return userStage.getUser();
    }

    private List<AnswerDto> askQuestions() {
        LOGGER.debug("Ask questions");
        return questionStage.askQuestions();
    }

    private void showResults(User user, List<AnswerDto> answers) {
        LOGGER.debug("Show results (answers count = {})", answers.size());
        resultStage.checkAnswersOnCorrect(user, answers);
    }
}