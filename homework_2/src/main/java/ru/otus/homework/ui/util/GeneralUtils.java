package ru.otus.homework.ui.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GeneralUtils {

    private Locale userLocale;

    private Locale appLocale;

    public GeneralUtils(@Value("${default.language}") String defaultLanguage) {
        this.userLocale = Locale.getDefault();
        this.appLocale = new Locale(defaultLanguage);
    }

    public Locale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public Locale getAppLocale() {
        return appLocale;
    }
}
