package ru.otus.homework.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import ru.otus.homework.question.dto.QuestionDto;

import java.util.List;
import java.util.Locale;

@Controller
public class QuestionControllerImpl implements QuestionController {
    private static final Logger LOGGER = LogManager.getLogger(QuestionControllerImpl.class);

    private final QuestionService questionService;

    public QuestionControllerImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public List<QuestionDto> getQuestions(Locale locale) {
        LOGGER.info("Get all questions");
         return questionService.getQuestionDtos(locale);
    }
}
