package ru.otus.homework.ui.interlocutor.io.output;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {

    @Override
    public void writeln(String message) {
        System.out.println(message);
    }

    @Override
    public void write(String message) {
        System.out.print(message);
    }
}
