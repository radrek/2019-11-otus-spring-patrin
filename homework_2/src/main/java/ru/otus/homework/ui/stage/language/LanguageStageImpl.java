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

        Locale finalLocale = chooseLocale(localeUtils.getUserLocale());
        localeUtils.setUserLocale(finalLocale);
    }

    private Locale chooseLocale(Locale userLocale) {
        mediator.say("language.choose");
        showAllowedLanguages(userLocale);
        return getFinalLocale(userLocale);
    }

    private void showAllowedLanguages(Locale userLocale) {
        localeUtils.getAllowedLocales().forEach(locale -> {
            String messageCode;
            if (userLocale.getLanguage().equals(locale.getLanguage())) {
                messageCode = "language.variant.default";
            } else {
                messageCode = String.format("language.variant.%s", locale.getLanguage().toLowerCase());
            }
            mediator.say(messageCode);
        });
    }

    private Locale getFinalLocale(Locale userLocale) {
        Locale finalLocale = null;
        while (finalLocale == null) {
            String codeOrZero = mediator.ask("language.input.message");
            if (DEFAULT_LANGUAGE_CODE.equals(codeOrZero)) {
                finalLocale = userLocale;
            } else if (isAllowedLanguageCode(codeOrZero)) {
                finalLocale = localeUtils.getAllowedLocales()
                        .stream()
                        .filter(locale -> locale.getLanguage().equals(codeOrZero.toLowerCase()))
                        .findAny()
                        .orElse(null);
            } else {
                mediator.say("language.input.wrong");
            }
        }
        return finalLocale;
    }

    private boolean isAllowedLanguageCode(String codeOrZero) {
        return localeUtils.getAllowedLocales().stream().anyMatch(locale -> locale.getLanguage().equals(codeOrZero.toLowerCase()));
    }
}
