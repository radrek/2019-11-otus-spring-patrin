package ru.otus.homework.service.checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.ResultDto;
import ru.otus.homework.service.checker.additional.Status;
import ru.otus.homework.service.question.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckerServiceTest {

    private static final int NUMBER = 1;
    private static final String ANSWER = "Хорошо";

    @InjectMocks
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
    }

    @Test
    void shouldReturnCorrectResultDtoForNotFoundQuestion() {

        List<ResultDto> results = checkerService.checkAnswersOnCorrect(answers);

        assertThat(results).as("Check result collection on correct if question not found for answer")
                .hasSize(1)
                .flatExtracting(ResultDto::getNumber, ResultDto::getStatus)
                .containsExactly(1, Status.QUESTION_NOT_FOUND);
    }

    @Test
    void shouldReturnCorrectResultDtoForCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn(ANSWER);
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions()).thenReturn(List.of(question));

        List<ResultDto> results = checkerService.checkAnswersOnCorrect(answers);

        assertThat(results).as("Check result collection on correct if question  found and answer is correct")
                .hasSize(1)
                .flatExtracting(ResultDto::getNumber, ResultDto::getStatus)
                .containsExactly(1, Status.OK);
    }

    @Test
    void shouldReturnCorrectResultDtoForNotCorrectAnswer() {
        when(question.getCorrectAnswer()).thenReturn("Not correct");
        when(question.getNumber()).thenReturn(NUMBER);
        when(questionService.getQuestions()).thenReturn(List.of(question));

        List<ResultDto> results = checkerService.checkAnswersOnCorrect(answers);

        assertThat(results).as("Check result collection on correct if question found and answer is not correct")
                .hasSize(1)
                .flatExtracting(ResultDto::getNumber, ResultDto::getStatus)
                .containsExactly(1, Status.FAIL);
    }

    @Test
    void shouldReturnCorrectResultsDto() {
        List<AnswerDto> answers = List.of(new AnswerDto(NUMBER, ANSWER),
                new AnswerDto(2, "Нормасик"));

        List<ResultDto> results = checkerService.checkAnswersOnCorrect(answers);

        assertThat(results).as("Check result collection on correct if questions not found")
                .hasSize(2)
                .flatExtracting(ResultDto::getNumber, ResultDto::getStatus)
                .containsExactly(1, Status.QUESTION_NOT_FOUND,
                        2, Status.QUESTION_NOT_FOUND);
    }
}