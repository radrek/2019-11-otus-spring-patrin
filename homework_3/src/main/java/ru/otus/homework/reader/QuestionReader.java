package ru.otus.homework.reader;

import ru.otus.homework.question.Question;

import java.util.List;
import java.util.Locale;

public interface QuestionReader {
    List<Question> readQuestions(Locale locale);
}
