package ru.otus.homework.ui.stage.language;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("language")
public class LanguageSettings {

    private String defaultValue;

    private String bundlePathPattern;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getBundlePathPattern() {
        return bundlePathPattern;
    }

    public void setBundlePathPattern(String bundlePathPattern) {
        this.bundlePathPattern = bundlePathPattern;
    }
}
