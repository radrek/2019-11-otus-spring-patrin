package ru.otus.homework.service.checker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.checker.CheckerService;
import ru.otus.homework.checker.additional.Status;
import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.checker.dto.ResultDto;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@TestPropertySource("/application_test.properties")
class CheckerServiceIT {

    private static final int PASSING_SCORE = 2;

    @Autowired
    private CheckerService checkerService;

    @Test
    void mustPasseTestWithTwoCorrectAnsewrForDefaultQuestions() {
        AnswerDto firstAnswer = new AnswerDto(1, "Хорошо");
        AnswerDto secondAnswer = new AnswerDto(2, "Да");
        AnswerDto thirdAnswer = new AnswerDto(3, "Нет");
        AnswerDto fourthAnswer = new AnswerDto(4, "Нет");
        AnswerDto fifthAnswer = new AnswerDto(5, "Нет");
        List<AnswerDto> answers = Arrays.asList(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, fifthAnswer);

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, null);

        assertThat(resultDto)
                .as("Correct answer count equal %d", PASSING_SCORE)
                .hasFieldOrPropertyWithValue("score", PASSING_SCORE)

                .as("PassingScore must be equal to %d", PASSING_SCORE)
                .hasFieldOrPropertyWithValue("passingScore", resultDto.getScore())

                .as("Question result count is equal to %d", answers.size())
                .extracting("questionResults").asList()
                .hasSize(answers.size())

                .as("Question results must contains five numbers and two 'OK'. one 'FAIL', two 'NOT FOUNT'")
                .extracting("number", "status")
                .contains(tuple(1, Status.OK),
                        tuple(2, Status.OK),
                        tuple(3, Status.FAIL),
                        tuple(4, Status.QUESTION_NOT_FOUND),
                        tuple(5, Status.QUESTION_NOT_FOUND));
    }

    @Test
    void mustNotPasseTestWithOneCorrectAnsewrForDefaultQuestions() {
        AnswerDto firstAnswer = new AnswerDto(1, "Хорошо");
        AnswerDto secondAnswer = new AnswerDto(2, "Нет");

        List<AnswerDto> answers = Arrays.asList(firstAnswer, secondAnswer);

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, null);

        assertThat(resultDto)
                .as("Correct answer count equal %d", 1)
                .hasFieldOrPropertyWithValue("score", 1)

                .as("PassingScore must be equal to %d", PASSING_SCORE)
                .hasFieldOrPropertyWithValue("passingScore", PASSING_SCORE)

                .as("Question result count is equal to %d", answers.size())
                .extracting("questionResults").asList()
                .hasSize(answers.size())

                .as("Question results must contains two numbers and one statuses 'OK'")
                .extracting("number", "status")
                .contains(tuple(1, Status.OK),
                        tuple(2, Status.FAIL));
    }

    @Test
    void mustNotPasseTestBecauseAnswersHasDuplicates() {
        AnswerDto firstAnswer = new AnswerDto(1, "Хорошо");
        AnswerDto secondAnswer = new AnswerDto(1, "Хорошо");

        List<AnswerDto> answers = Arrays.asList(firstAnswer, secondAnswer);

        ResultDto resultDto = checkerService.checkAnswersOnCorrect(answers, null);

        assertThat(resultDto)
                .as("Correct answer count equal %d", 1)
                .hasFieldOrPropertyWithValue("score", 1)

                .as("PassingScore must be equal to %d", PASSING_SCORE)
                .hasFieldOrPropertyWithValue("passingScore", PASSING_SCORE)

                .as("Question result count is equal to %d", answers.size())
                .extracting("questionResults").asList()
                .hasSize(answers.size())

                .as("Question results must contains two numbers and one statuses 'OK' and one 'DUPLICATE")
                .extracting("number", "status")
                .contains(tuple(1, Status.OK),
                        tuple(1, Status.DUPLICATE));
    }
}
