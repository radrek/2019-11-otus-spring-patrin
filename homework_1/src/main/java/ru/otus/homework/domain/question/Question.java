package ru.otus.homework.domain.question;

public interface Question {

    int getNumber();

    QuestionType getType();

    String getQuestion();

    String getCorrectAnswer();
}
