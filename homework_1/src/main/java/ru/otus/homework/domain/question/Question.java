package ru.otus.homework.domain.question;

public interface Question {

    QuestionType getType();

    String getQuestion();

    String getCorrectAnswer();
}
