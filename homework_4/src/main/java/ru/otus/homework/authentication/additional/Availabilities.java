package ru.otus.homework.authentication.additional;

import org.springframework.shell.Availability;

public class Availabilities {
    public static Availability ALREADY_LOGIN = Availability.unavailable("Logout from current session");
    public static Availability NOT_LOGIN = Availability.unavailable("Current session not found");
    public static Availability SUCCESS = Availability.available();
}
