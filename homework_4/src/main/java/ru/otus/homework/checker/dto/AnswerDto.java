package ru.otus.homework.checker.dto;

public class AnswerDto {
    private final int number;
    private final String answer;

    public AnswerDto(int number, String answer) {
        this.number = number;
        this.answer = answer;
    }

    public int getNumber() {
        return number;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "number=" + number +
                ", answer='" + answer + '\'' +
                '}';
    }
}
