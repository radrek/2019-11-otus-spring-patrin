package ru.otus.homework.service.checker;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.otus.homework.checker",
        "ru.otus.homework.question",
        "ru.otus.homework.reader",
        "ru.otus.homework.language"})
@SpringBootConfiguration
@EnableConfigurationProperties
public class TestSpringBootConfiguration {
}
