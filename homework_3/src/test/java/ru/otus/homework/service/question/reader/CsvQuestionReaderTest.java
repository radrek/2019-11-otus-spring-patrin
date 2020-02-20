package ru.otus.homework.service.question.reader;

import org.junit.jupiter.api.Test;
import ru.otus.homework.question.Question;
import ru.otus.homework.reader.CsvQuestionReader;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class CsvQuestionReaderTest {
    private static final String TEST_CSV_PATH = "test_%s.csv";
    private static final String DEFAULT_LANGUAGE = "ru";

    private CsvQuestionReader csvQuestionReader;

    @Test
    void shouldReturnNullIfMethodThrowException() {
        csvQuestionReader = new CsvQuestionReader(null, null);

        List<Question> questions = csvQuestionReader.readQuestions(Locale.ENGLISH);

        assertThat(questions).as("Check collection on null if method throw exception")
                .isNull();
    }

    @Test
    void shouldReturnCorrectQuestionCollection() {
        csvQuestionReader = new CsvQuestionReader(TEST_CSV_PATH, DEFAULT_LANGUAGE);
        Locale locale = new Locale(DEFAULT_LANGUAGE);

        List<Question> questions = csvQuestionReader.readQuestions(locale);

        String rowDescriptionTemplate = "Contains %s row number, question and correct answer";
        assertThat(questions).as("Check collection on not empty")
                .hasSize(2)
                .flatExtracting(Question::getNumber, Question::getQuestion, Question::getCorrectAnswer)
                .as(rowDescriptionTemplate, "first")
                .contains(1, "Как дела?", "Хорошо")
                .as(rowDescriptionTemplate, "second")
                .contains(2, "Удачно?", "Да");
    }

    @Test
    void shouldReturnDefaultQuestionCollectionForNotAllowedLocale() {
        csvQuestionReader = new CsvQuestionReader(TEST_CSV_PATH, DEFAULT_LANGUAGE);
        Locale locale = Locale.JAPANESE;

        List<Question> questions = csvQuestionReader.readQuestions(locale);

        String rowDescriptionTemplate = "Contains %s row number, question and correct answer";
        assertThat(questions).as("Check collection on not empty for not allowed locale = %s", locale)
                .hasSize(2)
                .flatExtracting(Question::getNumber, Question::getQuestion, Question::getCorrectAnswer)
                .as(rowDescriptionTemplate, "first")
                .contains(1, "Как дела?", "Хорошо")
                .as(rowDescriptionTemplate, "second")
                .contains(2, "Удачно?", "Да");
    }
}