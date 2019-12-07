package ru.otus.homework.service.question.reader;

import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.question.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvQuestionReaderTest {
    public static final String TEST_CSV_PATH = "test.csv";

    private CsvQuestionReader csvQuestionReader;

    @Test
    void shouldReturnEmptyQuestionCollectionIfThrowException() {
        csvQuestionReader = new CsvQuestionReader("");

        List<Question> questions = csvQuestionReader.readQuestions();

        assertThat(questions).as("Check collection on empty if method throw exception")
                .isNull();
    }

    @Test
    void shouldReturnCorrectQuestionCollection() {
        csvQuestionReader = new CsvQuestionReader(TEST_CSV_PATH);

        List<Question> questions = csvQuestionReader.readQuestions();

        String rowDescriptionTemplate = "Contains %s row number, question and correct answer";
        assertThat(questions).as("Check collection on not empty")
                .hasSize(2)
                .flatExtracting(Question::getNumber, Question::getQuestion, Question::getCorrectAnswer)
                .as(rowDescriptionTemplate, "first")
                .contains(1, "Как дела?", "Хорошо")
                .as(rowDescriptionTemplate, "second")
                .contains(2, "Удачно?", "Да");
    }
}