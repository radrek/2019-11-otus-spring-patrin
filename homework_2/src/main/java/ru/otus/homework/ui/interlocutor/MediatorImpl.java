package ru.otus.homework.ui.interlocutor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.interlocutor.io.input.Input;
import ru.otus.homework.ui.interlocutor.io.output.Output;

@Service
public class MediatorImpl implements Mediator {
    private final Input input;
    private final Output output;

    public MediatorImpl(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void say(String message) {
        output.writeln(message);
    }

    @Override
    public void keepTalking(String message) {
        output.write(message);
    }

    @Override
    public String ask(String question) {
        keepTalking(question);
        return input.readLine();
    }

    @Override
    public void askWithoutAnswer(String question) {
        say(question);
    }

    @Override
    public String KeepAskingUntilGetAnswer(String question) {
        String result;
        while (true) {
            result = ask(question);
            if (Strings.isNotBlank(result)) {
                return result;
            }
            say("Empty string is not allowed.");
        }
    }
}
