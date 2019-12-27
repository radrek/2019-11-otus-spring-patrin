package ru.otus.homework;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.homework.ui.UserInterface;

@PropertySource("classpath:app.properties")
@Configuration
@ComponentScan
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        UserInterface userInterface = context.getBean(UserInterface.class);
        userInterface.start();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInApp() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
