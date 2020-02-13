package ru.otus.homework.ui.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework.ui.stage.language.exception.NotAllowedLanguagesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LocaleUtils {
    private static final Logger LOGGER = LogManager.getLogger(LocaleUtils.class);
    private static final String BUNDLE_FILE_NAME_PATTERN = "messages_([A-Za-z]{2,}).properties$";
    private static final String BUNDLE_FILE_PATH_PATTERN = "classpath:/i18n/messages_*.properties";

    private Locale userLocale;

    private Locale appLocale;

    private ResourceLoader resourceLoader;

    public LocaleUtils(@Value("${default.language}") String defaultLanguage, ResourceLoader resourceLoader) {
        this.userLocale = Locale.getDefault();
        this.appLocale = new Locale(defaultLanguage);
        this.resourceLoader = resourceLoader;
        getAllowedLocales();
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

    public List<Locale> getAllowedLocales() {
        Resource[] resources = loadBundleResources();
        if (ArrayUtils.isEmpty(resources)) {
            throw new NotAllowedLanguagesException();
        } else {
            return createLocalesFromFileNames(resources);
        }
    }

    private List<Locale> createLocalesFromFileNames(Resource[] resources) {
        List<Locale> locales = new ArrayList<>();
        Pattern pattern = Pattern.compile(BUNDLE_FILE_NAME_PATTERN);
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            Matcher matcher = pattern.matcher(filename);
            if (matcher.find()) {
                locales.add(new Locale(matcher.group(1)));
            }
        }
        return locales;
    }

    private Resource[] loadBundleResources() {
        try {
            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources(BUNDLE_FILE_PATH_PATTERN);
        } catch (Exception e) {
            LOGGER.error("Can't load resources", e);
        }
        return null;
    }

}
