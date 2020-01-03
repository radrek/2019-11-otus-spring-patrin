package ru.otus.homework.ui.stage.question;

import ru.otus.homework.dto.AnswerDto;

import java.util.List;

public interface QuestionStage {
    List<AnswerDto> askQuestions();
}
