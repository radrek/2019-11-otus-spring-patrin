package ru.otus.homework.ui.interlocutor.io.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Scanner in;

    public ConsoleInput() {
        this.in = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return in.nextLine();
    }
}
