package ru.otus.homework.question;

import ru.otus.homework.question.dto.QuestionDto;

import java.util.List;
import java.util.Locale;

public interface QuestionController {
    List<QuestionDto> getQuestions(Locale locale);
}
