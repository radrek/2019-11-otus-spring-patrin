package ru.otus.homework.controller.checker;

import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.ResultDto;

import java.util.List;
import java.util.Locale;

public interface CheckerController {
    List<ResultDto> checkAnswersOnCorrect(List<AnswerDto> answers, Locale locale);
}
