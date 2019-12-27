package ru.otus.homework.controller.question;

import ru.otus.homework.dto.QuestionDto;

import java.util.List;

public interface QuestionController {
    List<QuestionDto> getQuestions();
}
