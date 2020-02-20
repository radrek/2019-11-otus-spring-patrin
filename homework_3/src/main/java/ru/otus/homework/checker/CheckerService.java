package ru.otus.homework.checker;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.checker.additional.Status;
import ru.otus.homework.checker.dto.AnswerDto;
import ru.otus.homework.checker.dto.ResultDto;
import ru.otus.homework.question.Question;
import ru.otus.homework.question.QuestionResult;
import ru.otus.homework.question.QuestionService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckerService {
    private static final Logger LOGGER = LogManager.getLogger(CheckerService.class);

    private final QuestionService questionService;
    private final int passingScore;

    public CheckerService(QuestionService questionService, CheckerSettings settings) {
        this.questionService = questionService;
        this.passingScore = settings.getPassingScore();
    }

    public ResultDto checkAnswersOnCorrect(List<AnswerDto> answers, Locale locale) {
        LOGGER.info("Check answers on correct");
        final List<Question> questions = questionService.getQuestions(locale);
        return getResult(answers, questions);
    }

    private ResultDto getResult(List<AnswerDto> answers, List<Question> questions) {
        final ResultDto result = new ResultDto();
        List<QuestionResult> questionResults = getQuestionResultsAndCountCorrectAnswer(answers, questions, result);
        result.setQuestionResults(questionResults);
        result.setPassingScore(passingScore);
        return result;
    }

    private List<QuestionResult> getQuestionResultsAndCountCorrectAnswer(List<AnswerDto> answers,
                                                                         List<Question> questions,
                                                                         ResultDto result) {
        return answers.stream()
                .filter(answer -> answer != null && StringUtils.isNotBlank(answer.getAnswer()))
                .map(answer -> {
                    QuestionResult questionResult = getQuestionResult(questions, answer);
                    plusOneScoreForCorrectAnswer(result, questionResult);
                    return questionResult;
                }).collect(Collectors.toList());
    }

    private QuestionResult getQuestionResult(List<Question> questions, AnswerDto answer) {
        Optional<Question> questionByNumber = getQuestionByNumber(questions, answer.getNumber());
        return getQuestionResult(answer, questionByNumber.orElse(null));
    }

    private Optional<Question> getQuestionByNumber(List<Question> questions, int number) {
        return questions.stream()
                .filter(question -> question.getNumber() == number)
                .findAny();
    }

    private QuestionResult getQuestionResult(AnswerDto answer, Question question) {
        if (question != null) {
            return new QuestionResult(question.getNumber(),
                    checkUserAnswerOnCorrect(answer.getAnswer(), question.getCorrectAnswer()));
        } else {
            LOGGER.warn("Can't find question with number = {}", answer.getNumber());
            return new QuestionResult(answer.getNumber(), Status.QUESTION_NOT_FOUND);
        }
    }

    private Status checkUserAnswerOnCorrect(String userAnswer, String correctAnswer) {
        if (correctAnswer.equals(userAnswer)) {
            return Status.OK;
        } else {
            return Status.FAIL;
        }
    }

    private void plusOneScoreForCorrectAnswer(ResultDto result, QuestionResult questionResult) {
        if (Status.OK.equals(questionResult.getStatus())) {
            result.setScore(result.getScore() + 1);
        }
    }
}
