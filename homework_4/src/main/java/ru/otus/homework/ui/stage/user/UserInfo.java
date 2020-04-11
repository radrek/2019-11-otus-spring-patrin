package ru.otus.homework.ui.stage.user;

import ru.otus.homework.user.User;

public class UserInfo {
    private final String firstName;
    private final String secondName;

    public UserInfo(User user) {
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
