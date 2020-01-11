package ru.otus.homework.service.question.reader;

import ru.otus.homework.domain.question.Question;

import java.util.List;
import java.util.Locale;

public interface QuestionReader {
    List<Question> readQuestions(Locale locale);
}
