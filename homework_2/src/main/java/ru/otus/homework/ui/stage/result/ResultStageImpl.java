package ru.otus.homework.ui.stage.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.controller.checker.CheckerController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.result.QuestionResult;
import ru.otus.homework.dto.result.ResultDto;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.user.additional.User;
import ru.otus.homework.ui.util.LocaleUtils;

import java.util.List;

@Service
public class ResultStageImpl implements ResultStage {
    private static final Logger LOGGER = LogManager.getLogger(ResultStageImpl.class);

    private final CheckerController checkerController;
    private final Mediator mediator;
    private final LocaleUtils localeUtils;

    public ResultStageImpl(CheckerController checkerController, Mediator mediator, LocaleUtils localeUtils) {
        this.checkerController = checkerController;
        this.mediator = mediator;
        this.localeUtils = localeUtils;
    }

    @Override
    public void checkAnswersOnCorrect(User user, List<AnswerDto> answers) {
        checkOnErrors(user, answers);
        LOGGER.info("Check answers on correct where count answers = {}", answers.size());
        ResultDto result = checkerController.checkAnswersOnCorrect(answers, localeUtils.getUserLocale());
        checkResultOnNull(result.getQuestionResults());
        showStartMessage(user);
        showResult(result);
    }

    private void checkOnErrors(User user, List<AnswerDto> answers) {
        if (user == null) {
            LOGGER.error("User can't be null");
            throw new NullPointerException("User can't be null");
        }
        if (answers == null) {
            LOGGER.error("Answers can't be null");
            throw new NullPointerException("Answers can't be null");
        }
    }

    private void checkResultOnNull(List<QuestionResult> questionResults) {
        if (questionResults == null) {
            LOGGER.error("Results can't be null");
            throw new NullPointerException("Results can't be null");
        }
    }

    private void showStartMessage(User user) {
        mediator.say("result.message", user.getFirstName(), user.getSecondName());
    }

    private void showResult(ResultDto result) {
        List<QuestionResult> questionResults = result.getQuestionResults();
        showQuestionResults(questionResults);
        showPassingResult(result.getPassingScore(), result.getScore());
    }

    private void showQuestionResults(List<QuestionResult> questionResults) {
        LOGGER.debug("Results count = {}", questionResults.size());
        questionResults
                .forEach(resultDto -> {
                    String resultCode;
                    switch (resultDto.getStatus()) {
                        case OK:
                            resultCode = "result.correct";
                            break;
                        case FAIL:
                            resultCode = "result.fail";
                            break;
                        case QUESTION_NOT_FOUND:
                        default:
                            resultCode = "result.question.unknown";
                            break;
                    }
                    mediator.say(resultCode, resultDto.getNumber());
                });
    }

    private void showPassingResult(int passingScore, int score) {
        String resultMessage;
        if (passingScore > score) {
            resultMessage = "result.passing.fail";
        } else {
            resultMessage = "result.passing.success";
        }
        mediator.say(resultMessage, score);
    }
}
