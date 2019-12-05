package ru.otus.homework.dto;

import ru.otus.homework.service.checker.additional.Status;

public class ResultDto {
    private final int number;
    private final Status status;

    public ResultDto(int number, Status status) {
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
        return "ResultDto{" +
                "number=" + number +
                ", status=" + status +
                '}';
    }
}
