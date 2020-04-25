package ru.otus.homework.question;

import ru.otus.homework.checker.additional.Status;

public class QuestionResult {
    private final int number;
    private final Status status;

    public QuestionResult(int number, Status status) {
        this.number = number;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "QuestionResult{" +
                "number=" + number +
                ", status=" + status +
                '}';
    }
}
