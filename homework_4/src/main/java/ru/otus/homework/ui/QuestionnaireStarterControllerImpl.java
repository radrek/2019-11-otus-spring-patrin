package ru.otus.homework.ui;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.authentication.AuthenticationController;
import ru.otus.homework.authentication.additional.Availabilities;
import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.ui.stage.greeting.GreetingStage;
import ru.otus.homework.ui.stage.language.LanguageStage;
import ru.otus.homework.ui.stage.question.QuestionStage;
import ru.otus.homework.ui.stage.result.ResultStage;
import ru.otus.homework.ui.stage.user.UserInfo;
import ru.otus.homework.user.User;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class QuestionnaireStarterControllerImpl implements QuestionnaireStarterController {
    private static final Logger LOGGER = LogManager.getLogger(QuestionnaireStarterControllerImpl.class);

    private final LanguageStage languageStage;
    private final GreetingStage greetingStage;
    private final QuestionStage questionStage;
    private final ResultStage resultStage;
    private final AuthenticationController authenticationController;
    private UserInfo userInfo;

    @ShellMethod(value = "Start questionnaire", key = {"s", "start"})
    @ShellMethodAvailability(value = "isAlreadyLogin")
    @Override
    public void start() {
        LOGGER.info("Start user interface");
        chooseLanguage();
        showGreeting();
        List<AnswerDto> answers = askQuestions();
        showResults(userInfo, answers);
    }

    private Availability isAlreadyLogin() {
        User user = authenticationController.getUser();
        if (user != null) {
            userInfo = new UserInfo(user);
            return Availabilities.SUCCESS;
        } else {
            return Availabilities.NOT_LOGIN;
        }
    }

    private void chooseLanguage() {
        LOGGER.debug("Choose language");
        languageStage.chooseLanguage();
    }

    private void showGreeting() {
        LOGGER.debug("Show greeting");
        greetingStage.showGreeting();
    }

    private List<AnswerDto> askQuestions() {
        LOGGER.debug("Ask questions");
        return questionStage.askQuestions();
    }

    private void showResults(UserInfo userInfo, List<AnswerDto> answers) {
        LOGGER.debug("Show results (answers count = {})", answers.size());
        resultStage.checkAnswersOnCorrect(userInfo, answers);
    }
}
