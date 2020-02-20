package ru.otus.homework.ui.interlocutor.additional;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import java.util.Locale;

public class LocaleMessageSourceResolvable implements MessageSourceResolvable {

    private final MessageSource messageSource;
    private final String[] code = new String[1];
    private final Object[] args;
    private final Locale defaultLocale;

    public LocaleMessageSourceResolvable(MessageSource messageSource, String code, Object[] args, Locale defaultLocale) {
        this.messageSource = messageSource;
        this.code[0] = code;
        this.args = args;
        this.defaultLocale = defaultLocale;
    }

    @Override
    public String[] getCodes() {
        return code;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public String getDefaultMessage() {
        return messageSource.getMessage(code[0], args, defaultLocale);
    }
}
