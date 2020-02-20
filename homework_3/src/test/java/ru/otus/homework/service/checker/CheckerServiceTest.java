package ru.otus.homework.service.checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.checker.CheckerService;
import ru.otus.homework.checker.CheckerSettings;
import ru.otus.homework.checker.additional.Status;
import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.checker.dto.ResultDto;
import ru.otus.homework.question.Question;
import ru.otus.homework.question.QuestionResult;
import ru.otus.homework.question.QuestionService;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckerServiceTest {

    private static final int NUMBER = 1;
    private static final String ANSWER = "Хорошо";
    private static final int PASSING_SCORE = 2;

    private CheckerService checkerService;

    @Mock
    private QuestionService questionService;

    @Mock
    private Question question;

    private List<AnswerDto> answers;

    @BeforeEach
    void setUp() {
        AnswerDto answer = new AnswerDto(NUMBER, ANSWER);
        this.answers = List.of(answer);

        CheckerSettings checkerSettings = new CheckerSettings();
        checkerSettings.setPassingScore(PASSING_SCORE);

        checkerService = new CheckerService(questionService, checkerSettings);
    }

    @Test
    void shouldReturnCorrectQuestionResultForNotFoundQuestion() {

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);
        List<QuestionResult> results = resultDto.getQuestionResults();

        assertThat(results).as("Check result collection on correct if question not found for answer")
                .hasSize(1)
                .flatExtracting(QuestionResult::getNumber, QuestionResult::getStatus)
                .containsExactly(1, Status.QUESTION_NOT_FOUND);
    }

    @Test
    void shouldReturnCorrectQuestionResultForCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn(ANSWER);
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions(Locale.ROOT)).thenReturn(List.of(question));

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);
        List<QuestionResult> results = resultDto.getQuestionResults();

        assertThat(results).as("Check result collection on correct if question  found and answer is correct")
                .hasSize(1)
                .flatExtracting(QuestionResult::getNumber, QuestionResult::getStatus)
                .containsExactly(1, Status.OK);
    }

    @Test
    void shouldReturnCorrectQuestionResultsForNotCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn("Not correct");
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions(Locale.ROOT)).thenReturn(List.of(question));

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);
        List<QuestionResult> results = resultDto.getQuestionResults();

        assertThat(results).as("Check result collection on correct if question found and answer is not correct")
                .hasSize(1)
                .flatExtracting(QuestionResult::getNumber, QuestionResult::getStatus)
                .containsExactly(1, Status.FAIL);
    }

    @Test
    void shouldReturnCorrectQuestionResults() {
        List<AnswerDto> answers = List.of(new AnswerDto(NUMBER, ANSWER),
                new AnswerDto(2, "Нормасик"));

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);
        List<QuestionResult> results = resultDto.getQuestionResults();

        assertThat(results).as("Check result collection on correct if questions not found")
                .hasSize(2)
                .flatExtracting(QuestionResult::getNumber, QuestionResult::getStatus)
                .containsExactly(1, Status.QUESTION_NOT_FOUND,
                        2, Status.QUESTION_NOT_FOUND);
    }

    @Test
    void shouldReturnOneScoreForOneCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn(ANSWER);
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions(Locale.ROOT)).thenReturn(List.of(question));

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);

        assertThat(resultDto.getScore())
                .as("The number of correct answers must be equal to one")
                .isEqualTo(1);
    }

    @Test
    void shouldReturnZeroScoreForNotCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn("Not correct");
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions(Locale.ROOT)).thenReturn(List.of(question));

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);

        assertThat(resultDto.getScore())
                .as("The number of correct answers must be equal to zero")
                .isEqualTo(0);
    }

    @Test
    void shouldReturnCorrectPassingScore() {

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, Locale.ROOT);

        assertThat(resultDto.getPassingScore())
                .as("Passing score must be equals %d", PASSING_SCORE)
                .isEqualTo(PASSING_SCORE);
    }
}