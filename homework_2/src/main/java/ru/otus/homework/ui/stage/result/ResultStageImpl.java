package ru.otus.homework.ui.stage.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.controller.checker.CheckerController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.ResultDto;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.user.additional.User;

import java.util.List;

public class ResultStageImpl implements ResultStage {
    private static final Logger LOGGER = LogManager.getLogger(ResultStageImpl.class);

    private final CheckerController checkerController;
    private final Mediator mediator;

    public ResultStageImpl(CheckerController checkerController, Mediator mediator) {
        this.checkerController = checkerController;
        this.mediator = mediator;
    }

    @Override
    public void checkAnswersOnCorrect(User user, List<AnswerDto> answers) {
        checkOnErrors(user, answers);
        LOGGER.info("Check answers on correct where count answers = {}", answers.size());
        List<ResultDto> resultDtos = checkerController.checkAnswersOnCorrect(answers);
        checkResultOnNull(resultDtos);
        showStartMessage(user);
        showResults(resultDtos);
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

    private void checkResultOnNull(List<ResultDto> resultDtos) {
        if (resultDtos == null) {
            LOGGER.error("Results can't be null");
            throw new NullPointerException("Results can't be null");
        }
    }

    private void showStartMessage(User user) {
        mediator.say("result.message", user.getFirstName(), user.getSecondName());
    }

    private void showResults(List<ResultDto> resultDtos) {
        LOGGER.debug("Results count = {}", resultDtos.size());
        resultDtos
                .forEach(resultDto -> {
                    String result;
                    switch (resultDto.getStatus()) {
                        case OK:
                            result = "result.correct";
                            break;
                        case FAIL:
                            result = "result.fail";
                            break;
                        case QUESTION_NOT_FOUND:
                        default:
                            result = "result.question.unknown";
                            break;
                    }
                    mediator.say(result, resultDto.getNumber());
                });
    }
}
