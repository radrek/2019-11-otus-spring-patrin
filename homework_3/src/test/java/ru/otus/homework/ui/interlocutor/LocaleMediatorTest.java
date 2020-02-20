package ru.otus.homework.ui.interlocutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.homework.ui.interlocutor.io.input.Input;
import ru.otus.homework.ui.interlocutor.io.output.Output;
import ru.otus.homework.ui.util.LocaleUtils;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocaleMediatorTest {

    private static final String BUNDLE_PATH = "/i18n/messages";
    private static final String USER_LANGUAGE = "ru";
    private static final String RESULT_MESSAGE = "Привет ru!";
    private static final Object[] ARGUMENTS = new String[]{USER_LANGUAGE};
    private static final String MESSAGE_KEY = "hello";
    private static final String ANSWER = "OK";

    @InjectMocks
    private LocaleMediator mediator;

    @Spy
    private MessageSource messageSource = new ReloadableResourceBundleMessageSource();

    @Mock
    private LocaleUtils localeUtils;

    @Mock
    private Input input;

    @Spy
    private Output output;

    private static Stream<Arguments> provideMessageForLocale() {
        return Stream.of(
                Arguments.of("Hello en!", "en"),
                Arguments.of(RESULT_MESSAGE, USER_LANGUAGE)
        );
    }

    @BeforeEach
    void setUp() {
        ReloadableResourceBundleMessageSource messageSource = (ReloadableResourceBundleMessageSource) this.messageSource;
        messageSource.setBasename(BUNDLE_PATH);
        messageSource.setDefaultEncoding("UTF-8");
        this.messageSource = messageSource;
        when(localeUtils.getUserLocale())
                .thenReturn(new Locale(USER_LANGUAGE));
    }

    @Test
    void shouldSayReturnCorrectMessage() {

        mediator.say(MESSAGE_KEY, ARGUMENTS);

        ArgumentCaptor<String> argumentMessage = ArgumentCaptor.forClass(String.class);
        verify(output).writeln(argumentMessage.capture());
        assertThat(argumentMessage.getValue())
                .as("Message is equal %s for language = %s", RESULT_MESSAGE, USER_LANGUAGE)
                .isEqualTo(RESULT_MESSAGE);
    }

    @Test
    void shouldKeepTalkingReturnCorrectMessage() {

        mediator.keepTalking(MESSAGE_KEY, ARGUMENTS);

        ArgumentCaptor<String> argumentMessage = ArgumentCaptor.forClass(String.class);
        verify(output).write(argumentMessage.capture());
        assertThat(argumentMessage.getValue())
                .as("Message is equal %s for language = %s", RESULT_MESSAGE, USER_LANGUAGE)
                .isEqualTo(RESULT_MESSAGE);
    }

    @Test
    void isCorrectAnswerToQuestion() {
        when(input.readLine()).thenReturn(ANSWER);

        String answer = mediator.ask(MESSAGE_KEY, ARGUMENTS);

        verify(output).write(RESULT_MESSAGE);
        assertThat(answer)
                .as("Answer on question is OK")
                .isEqualTo(ANSWER);

    }

    @ParameterizedTest
    @MethodSource("provideMessageForLocale")
    void shouldAskWithoutAnswerReturnMessageByLocale(String message, String language) {
        Locale locale = new Locale(language);
        when(localeUtils.getUserLocale())
                .thenReturn(locale);
        Object[] args = {language};

        mediator.askWithoutAnswer(MESSAGE_KEY, args);

        ArgumentCaptor<String> argumentMessage = ArgumentCaptor.forClass(String.class);
        verify(output).writeln(argumentMessage.capture());
        assertThat(argumentMessage.getValue())
                .as("Message is equal %s for language = %s", message, language)
                .isEqualTo(message);
    }

    @Test
    void isCorrectAnswerToQuestionWhenFirstTimeInputBlankString() {
        when(input.readLine())
                .thenReturn("")
                .thenReturn(ANSWER);

        String answer = mediator.keepAskingUntilGetAnswer(MESSAGE_KEY, ARGUMENTS);

        verify(output, times(2)).write(RESULT_MESSAGE);
        verify(output).writeln("Пусто");
        assertThat(answer)
                .as("Answer on question is OK")
                .isEqualTo(ANSWER);

    }
}