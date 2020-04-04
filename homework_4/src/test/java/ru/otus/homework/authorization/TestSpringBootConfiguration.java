package ru.otus.homework.authorization;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.otus.homework.authorization",
        "ru.otus.homework.user"})
@SpringBootConfiguration
public class TestSpringBootConfiguration {
}
