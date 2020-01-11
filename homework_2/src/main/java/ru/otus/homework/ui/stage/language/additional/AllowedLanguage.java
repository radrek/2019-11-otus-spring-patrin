package ru.otus.homework.ui.stage.language.additional;

import java.util.Locale;

public enum AllowedLanguage {
    RU(Locale.forLanguageTag("ru"), "Русский"),
    EN(Locale.ENGLISH, "English");

    private final Locale locale;
    private final String languageName;

    AllowedLanguage(Locale locale, String languageName) {
        this.locale = locale;
        this.languageName = languageName;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguageName() {
        return languageName;
    }
}
