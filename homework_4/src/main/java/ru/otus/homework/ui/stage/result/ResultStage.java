package ru.otus.homework.ui.stage.result;

import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.ui.stage.user.UserInfo;

import java.util.List;

public interface ResultStage {
    void checkAnswersOnCorrect(UserInfo userInfo, List<AnswerDto> answers);
}
