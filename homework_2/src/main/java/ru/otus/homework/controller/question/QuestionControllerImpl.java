package ru.otus.homework.controller.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.service.question.QuestionService;

import java.util.List;

public class QuestionControllerImpl implements QuestionController {
    private static final Logger LOGGER = LogManager.getLogger(QuestionControllerImpl.class);

    private final QuestionService questionService;

    public QuestionControllerImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public List<QuestionDto> getQuestions() {
        LOGGER.info("Get all questions");
         return questionService.getQuestionDtos();
    }
}
