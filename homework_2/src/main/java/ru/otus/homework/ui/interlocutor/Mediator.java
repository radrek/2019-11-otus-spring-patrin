package ru.otus.homework.ui.interlocutor;

public interface Mediator {
    void say(String message);

    void keepTalking(String message);

    String ask(String question);

    void askWithoutAnswer(String question);

    String KeepAskingUntilGetAnswer(String question);
}
