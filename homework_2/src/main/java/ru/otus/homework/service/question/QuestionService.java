package ru.otus.homework.service.question;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.service.question.reader.QuestionReader;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final Logger LOGGER = LogManager.getLogger(QuestionService.class);

    private final QuestionReader reader;

    public QuestionService(QuestionReader reader) {
        this.reader = reader;
    }

    public List<QuestionDto> getQuestionDtos() {
        LOGGER.info("Get all questions");
        List<Question> questions = getQuestions();

        return questions.stream()
                .map(QuestionDto::new)
                .collect(Collectors.toList());
    }

    public List<Question> getQuestions() {
        List<Question> questions = reader.readQuestions();
        if (CollectionUtils.isEmpty(questions)) {
            LOGGER.warn("Question collection is empty");
            return Collections.emptyList();
        }
        return questions;
    }
}
