package ru.otus.homework.question;

import ru.otus.homework.question.additional.QuestionType;

public interface Question {

    int getNumber();

    QuestionType getType();

    String getQuestion();

    String getCorrectAnswer();
}
