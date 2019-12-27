package ru.otus.homework.service.question;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.question.MultipleChoiceQuestion;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.service.question.reader.QuestionReader;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    private static final int QUESTION_NUMBER = 0;
    private static final String QUESTION = "Как дела?";
    private static final List<String> ANSWERS = List.of("Отлично", "Нормасики", "Меее");
    private static final String CORRECT_ANSWER = "Нормасики";

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionReader reader;

    @Test
    void shouldReturnEmptyQuestionCollectionForEmptyCollection() {
        when(reader.readQuestions()).thenReturn(Collections.emptyList());

        List<Question> questions = questionService.getQuestions();

        assertThat(questions).as("Check collection on empty if reader return empty collection")
                .isEmpty();
    }

    @Test
    void shouldReturnEmptyQuestionCollectionForNull() {
        when(reader.readQuestions()).thenReturn(null);

        List<Question> questions = questionService.getQuestions();

        assertThat(questions).as("Check collection on empty if reader return null")
                .isEmpty();
    }

    @Test
    void shouldReturnAllCorrectQuestions() {
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestionWithValue();
        List<Question> readerQuestions = List.of(multipleChoiceQuestion, new MultipleChoiceQuestion(),
                new MultipleChoiceQuestion());
        when(reader.readQuestions()).thenReturn(readerQuestions);

        List<Question> questions = questionService.getQuestions();

        assertThat(questions).as("Check collection size and contains specific question object")
                .hasSize(3)
                .containsOnlyOnce(multipleChoiceQuestion);
    }

    private MultipleChoiceQuestion createMultipleChoiceQuestionWithValue() {
        return createMultipleChoiceQuestion(QUESTION_NUMBER, QUESTION, CORRECT_ANSWER);
    }

    private MultipleChoiceQuestion createMultipleChoiceQuestion(int questionNumber, String questionString,
                                                                String correctAnswer) {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setNumber(questionNumber);
        question.setQuestion(questionString);
        question.setAnswers(QuestionServiceTest.ANSWERS);
        question.setCorrectAnswer(correctAnswer);
        return question;
    }

    @Test
    void shouldReturnAllCorrectQuestionDtos() {
        MultipleChoiceQuestion question1 = createMultipleChoiceQuestionWithValue();
        String questionStr2 = "Как курс?";
        MultipleChoiceQuestion question2 =
                createMultipleChoiceQuestion(1, questionStr2, "Отлично");
        List<Question> readerQuestions = List.of(question1, question2);
        when(reader.readQuestions()).thenReturn(readerQuestions);

        List<QuestionDto> questionDtos = questionService.getQuestionDtos();

        assertThat(questionDtos).as("Check dto collection size and contains specific questions: '%s', '%s'",
                QUESTION, questionStr2)
                .hasSize(2)
                .extracting(QuestionDto::getQuestion)
                .containsExactly(QUESTION, questionStr2);
    }

    @Test
    void shouldReturnEmptyDtoQuestionCollectionForEmptyCollection() {
        when(reader.readQuestions()).thenReturn(Collections.emptyList());

        List<QuestionDto> questionDtos = questionService.getQuestionDtos();

        assertThat(questionDtos).as("Check collection on empty if reader return empty collection")
                .isEmpty();
    }

    @Test
    void shouldReturnEmptyDtoQuestionCollectionForNull() {
        when(reader.readQuestions()).thenReturn(null);

        List<QuestionDto> questionDtos = questionService.getQuestionDtos();

        assertThat(questionDtos).as("Check collection on empty if reader return null")
                .isEmpty();
    }
}