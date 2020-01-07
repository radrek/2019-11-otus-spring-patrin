package ru.otus.homework.ui.interlocutor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.interlocutor.io.input.Input;
import ru.otus.homework.ui.interlocutor.io.output.Output;
import ru.otus.homework.ui.util.GeneralUtils;

@Service
public class LocaleMediator implements Mediator {
    private final Input input;
    private final Output output;
    private final GeneralUtils generalUtils;
    private final MessageSource messageSource;

    public LocaleMediator(Input input, Output output, GeneralUtils generalUtils, MessageSource messageSource) {
        this.input = input;
        this.output = output;
        this.generalUtils = generalUtils;
        this.messageSource = messageSource;
    }

    @Override
    public void say(String message, Object... args) {
        output.writeln(getLocaleMessage(message, args));
    }

    private String getLocaleMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, generalUtils.getLocale());
    }

    @Override
    public void keepTalking(String message, Object... args) {
        output.write(getLocaleMessage(message, args));
    }

    @Override
    public String ask(String question, Object... args) {
        keepTalking(question, args);
        return input.readLine();
    }

    @Override
    public void askWithoutAnswer(String question, Object... args) {
        say(question, args);
    }

    @Override
    public String KeepAskingUntilGetAnswer(String question, Object... args) {
        String result;
        while (true) {
            result = ask(question, args);
            if (Strings.isNotBlank(result)) {
                return result;
            }
            say("Empty string is not allowed.");
        }
    }
}
