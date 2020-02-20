package ru.otus.homework.ui.stage.result;

import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.ui.stage.user.additional.User;

import java.util.List;

public interface ResultStage {
    void checkAnswersOnCorrect(User user, List<AnswerDto> answers);
}
