package ru.otus.homework.controller.checker;

import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.result.ResultDto;

import java.util.List;
import java.util.Locale;

public interface CheckerController {
    ResultDto checkAnswersOnCorrect(List<AnswerDto> answers, Locale locale);
}
