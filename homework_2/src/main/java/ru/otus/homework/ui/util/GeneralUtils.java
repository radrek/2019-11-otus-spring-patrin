package ru.otus.homework.ui.util;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GeneralUtils {

    private Locale locale;

    public GeneralUtils() {
        this.locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
