package ru.otus.homework.language;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("language")
public class DefaultLanguageSettings {

    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
