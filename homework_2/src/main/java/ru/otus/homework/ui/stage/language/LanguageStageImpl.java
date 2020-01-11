package ru.otus.homework.ui.stage.language;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.language.additional.AllowedLanguage;
import ru.otus.homework.ui.util.GeneralUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@Service
public class LanguageStageImpl implements LanguageStage {
    private static final Logger LOGGER = LogManager.getLogger(LanguageStageImpl.class);
    public static final String DEFAULT_LANGUAGE_CODE = "0";

    private final Mediator mediator;
    private final GeneralUtils generalUtils;
    private final String defaultLanguage;


    public LanguageStageImpl(Mediator mediator, GeneralUtils generalUtils, @Value("${default.language}") String language) {
        this.mediator = mediator;
        this.generalUtils = generalUtils;
        this.defaultLanguage = language;
    }

    @Override
    public void chooseLanguage() {
        LOGGER.info("Choose language");

        AllowedLanguage defaultLanguage = getDefaultAllowedLanguage();
        generalUtils.setLocale(defaultLanguage.getLocale());

        AllowedLanguage finalLanguage = chooseLanguage(defaultLanguage);
        generalUtils.setLocale(finalLanguage.getLocale());
    }

    private AllowedLanguage getDefaultAllowedLanguage() {
        Locale userLocale = generalUtils.getLocale();
        AllowedLanguage language = EnumUtils.getEnumIgnoreCase(AllowedLanguage.class, userLocale.getLanguage());
        return Objects.requireNonNullElse(language, AllowedLanguage.valueOf(defaultLanguage));
    }

    private AllowedLanguage chooseLanguage(AllowedLanguage defaultLanguage) {
        mediator.say("language.choose");
        showAllowedLanguages(defaultLanguage);
        return getFinalLanguage(defaultLanguage);
    }

    private void showAllowedLanguages(AllowedLanguage defaultLanguage) {
        Arrays.stream(AllowedLanguage.values()).forEach(language -> {
            String messageCode;
            if (defaultLanguage.equals(language)) {
                messageCode = "language.variant.default";
            } else {
                messageCode = "language.variant";
            }
            mediator.say(messageCode, language.getLanguageName(), language.name());
        });
    }

    private AllowedLanguage getFinalLanguage(AllowedLanguage defaultLanguage) {
        AllowedLanguage finalLanguage = null;
        while (finalLanguage == null) {
            String codeOrZero = mediator.ask("language.input.message", defaultLanguage.getLanguageName());
            if (DEFAULT_LANGUAGE_CODE.equals(codeOrZero)) {
                finalLanguage = defaultLanguage;
            } else {
                finalLanguage = EnumUtils.getEnumIgnoreCase(AllowedLanguage.class, codeOrZero);
            }
            if (finalLanguage == null) {
                mediator.say("language.input.wrong", defaultLanguage.getLanguageName());
            }
        }
        return finalLanguage;
    }
}
