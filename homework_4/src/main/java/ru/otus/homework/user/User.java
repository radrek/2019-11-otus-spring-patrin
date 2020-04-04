package ru.otus.homework.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class User {
    private final String firstName;
    private final String secondName;
    private Integer score;
}
