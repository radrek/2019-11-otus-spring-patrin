package ru.otus.homework.controller.question;

import ru.otus.homework.dto.QuestionDto;

import java.util.List;
import java.util.Locale;

public interface QuestionController {
    List<QuestionDto> getQuestions(Locale locale);
}
