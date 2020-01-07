package ru.otus.homework.ui.interlocutor;

public interface Mediator {
    void say(String message, Object... args);

    void keepTalking(String message, Object... args);

    String ask(String question, Object... args);

    void askWithoutAnswer(String question, Object... args);

    String KeepAskingUntilGetAnswer(String question, Object... args);
}
