package ru.otus.homework.controller.checker;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.ResultDto;
import ru.otus.homework.service.checker.CheckerService;

import java.util.List;

public class CheckerControllerImpl implements CheckerController {
    private static final Logger LOGGER = LogManager.getLogger(CheckerControllerImpl.class);

    private final CheckerService checkerService;

    public CheckerControllerImpl(CheckerService checkerService) {
        this.checkerService = checkerService;
    }

    @Override
    public List<ResultDto> checkAnswersOnCorrect(List<AnswerDto> answers) {
        LOGGER.info("Check answers on correct");
        checkOnErrors(answers);
        return checkerService.checkAnswersOnCorrect(answers);
    }

    private void checkOnErrors(List<AnswerDto> answers) {
        if (CollectionUtils.isEmpty(answers)) {
            LOGGER.warn("Answers can't blank");
            throw new IllegalArgumentException("Answers can't blank");
        }
    }
}
