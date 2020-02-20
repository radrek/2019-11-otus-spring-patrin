package ru.otus.homework.checker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.checker.dto.ResultDto;

import java.util.List;
import java.util.Locale;

@Controller
public class CheckerControllerImpl implements CheckerController {
    private static final Logger LOGGER = LogManager.getLogger(CheckerControllerImpl.class);

    private final CheckerService checkerService;

    public CheckerControllerImpl(CheckerService checkerService) {
        this.checkerService = checkerService;
    }

    @Override
    public ResultDto checkAnswersOnCorrect(List<AnswerDto> answers, Locale locale) {
        LOGGER.info("Check answers on correct");
        checkOnErrors(answers);
        return checkerService.checkAnswersOnCorrect(answers, locale);
    }

    private void checkOnErrors(List<AnswerDto> answers) {
        if (CollectionUtils.isEmpty(answers)) {
            LOGGER.warn("Answers can't blank");
            throw new IllegalArgumentException("Answers can't blank");
        }
    }
}
