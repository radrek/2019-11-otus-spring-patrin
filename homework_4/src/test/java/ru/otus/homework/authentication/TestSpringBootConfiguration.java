package ru.otus.homework.authentication;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.otus.homework.authentication",
        "ru.otus.homework.user"})
@SpringBootConfiguration
public class TestSpringBootConfiguration {
}
