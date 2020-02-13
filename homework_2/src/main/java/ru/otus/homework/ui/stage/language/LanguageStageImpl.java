package ru.otus.homework.ui.stage.language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.util.LocaleUtils;

import java.util.Locale;

@Service
public class LanguageStageImpl implements LanguageStage {
    private static final Logger LOGGER = LogManager.getLogger(LanguageStageImpl.class);
    private static final String DEFAULT_LANGUAGE_CODE = "0";

    private final Mediator mediator;
    private final LocaleUtils localeUtils;


    public LanguageStageImpl(Mediator mediator, LocaleUtils localeUtils) {
        this.mediator = mediator;
        this.localeUtils = localeUtils;
    }

    @Override
    public void chooseLanguage() {
        LOGGER.info("Choose language");

        Locale finalLanguage = chooseLanguage(localeUtils.getUserLocale());
        localeUtils.setUserLocale(finalLanguage);
    }

    private Locale chooseLanguage(Locale defaultLanguage) {
        mediator.say("language.choose");
        showAllowedLanguages(defaultLanguage);
        return getFinalLanguage(defaultLanguage);
    }

    private void showAllowedLanguages(Locale defaultLanguage) {
        localeUtils.getAllowedLocales().forEach(language -> {
            String messageCode;
            if (defaultLanguage.getLanguage().equals(language.getLanguage())) {
                messageCode = "language.variant.default";
            } else {
                messageCode = String.format("language.variant.%s", language.getLanguage().toLowerCase());
            }
            mediator.say(messageCode);
        });
    }

    private Locale getFinalLanguage(Locale defaultLanguage) {
        Locale finalLanguage = null;
        while (finalLanguage == null) {
            String codeOrZero = mediator.ask("language.input.message");
            if (DEFAULT_LANGUAGE_CODE.equals(codeOrZero)) {
                finalLanguage = defaultLanguage;
            } else if (isAllowedLanguageCode(codeOrZero)) {
                finalLanguage = localeUtils.getAllowedLocales()
                        .stream()
                        .filter(locale -> locale.getLanguage().equals(codeOrZero.toLowerCase()))
                        .findAny()
                        .orElse(null);
            } else {
                mediator.say("language.input.wrong");
            }
        }
        return finalLanguage;
    }

    private boolean isAllowedLanguageCode(String codeOrZero) {
        return localeUtils.getAllowedLocales().stream().anyMatch(locale -> locale.getLanguage().equals(codeOrZero.toLowerCase()));
    }
}
