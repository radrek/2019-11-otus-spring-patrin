package ru.otus.homework.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework.question.dto.QuestionDto;
import ru.otus.homework.reader.QuestionReader;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final Logger LOGGER = LogManager.getLogger(QuestionService.class);

    private final QuestionReader reader;

    public QuestionService(QuestionReader reader) {
        this.reader = reader;
    }

    public List<QuestionDto> getQuestionDtos(Locale locale) {
        LOGGER.info("Get all questions");
        List<Question> questions = getQuestions(locale);

        return questions.stream()
                .map(QuestionDto::new)
                .collect(Collectors.toList());
    }

    public List<Question> getQuestions(Locale locale) {
        List<Question> questions = reader.readQuestions(locale);
        if (CollectionUtils.isEmpty(questions)) {
            LOGGER.warn("Question collection is empty");
            return Collections.emptyList();
        }
        return questions;
    }
}
