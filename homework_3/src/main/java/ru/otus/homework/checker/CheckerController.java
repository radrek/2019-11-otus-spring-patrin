package ru.otus.homework.checker;

import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.checker.dto.ResultDto;

import java.util.List;
import java.util.Locale;

public interface CheckerController {
    ResultDto checkAnswersOnCorrect(List<AnswerDto> answers, Locale locale);
}
