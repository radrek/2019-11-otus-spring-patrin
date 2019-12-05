package ru.otus.homework.service.checker;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.ResultDto;
import ru.otus.homework.service.checker.additional.Status;
import ru.otus.homework.service.question.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckerService {
    private static final Logger LOGGER = LogManager.getLogger(CheckerService.class);

    private final QuestionService questionService;

    public CheckerService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public List<ResultDto> checkAnswersOnCorrect(List<AnswerDto> answers) {
        LOGGER.info("Check answers on correct");
        final List<Question> questions = questionService.getQuestions();
        return answers.stream()
                .filter(answer -> answer != null && StringUtils.isNotBlank(answer.getAnswer()))
                .map(answer -> {
                    Optional<Question> questionByNumber = getQuestionByNumber(questions, answer.getNumber());
                    return getResultDto(answer, questionByNumber.orElse(null));
                }).collect(Collectors.toList());

    }

    private Optional<Question> getQuestionByNumber(List<Question> questions, int number) {
        return questions.stream()
                .filter(question -> question.getNumber() == number)
                .findAny();
    }

    private ResultDto getResultDto(AnswerDto answer, Question question) {
        if (question != null) {
            return new ResultDto(question.getNumber(),
                    checkUserAnswerOnCorrect(answer.getAnswer(), question.getCorrectAnswer()));
        } else {
            LOGGER.warn("Can't find question with number = {}", answer.getNumber());
            return new ResultDto(answer.getNumber(), Status.QUESTION_NOT_FOUND);
        }
    }

    private Status checkUserAnswerOnCorrect(String userAnswer, String correctAnswer) {
        if (correctAnswer.equals(userAnswer)) {
            return Status.OK;
        } else {
            return Status.FAIL;
        }
    }
}
