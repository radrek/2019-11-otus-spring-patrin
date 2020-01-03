package ru.otus.homework.ui.stage.question;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.controller.question.QuestionController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.ui.stage.question.additional.QuestionType;
import ru.otus.homework.ui.util.ConsoleUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class QuestionStageImpl implements QuestionStage {
    private static final Logger LOGGER = LogManager.getLogger(QuestionStageImpl.class);

    private final QuestionController questionController;
    private final ConsoleUtils consoleUtils;
    private final Scanner in;

    public QuestionStageImpl(QuestionController questionController, ConsoleUtils consoleUtils, Scanner in) {
        this.questionController = questionController;
        this.consoleUtils = consoleUtils;
        this.in = in;
    }

    @Override
    public List<AnswerDto> askQuestions() {
        LOGGER.info("Ask questions");
        List<QuestionDto> questions = questionController.getQuestions();
        checkQuestionsOnNull(questions);
        return askQuestions(questions);
    }

    private void checkQuestionsOnNull(List<QuestionDto> questions) {
        if (questions == null) {
            LOGGER.error("Questions can't be null");
            throw new NullPointerException("Questions can't be null");
        }
    }

    private List<AnswerDto> askQuestions(List<QuestionDto> questions){
        List<AnswerDto> answers = new ArrayList<>();
        for (QuestionDto question : questions) {
            if (QuestionType.MULTIPLE_CHOICE.name().equals(question.getQuestionType().name())) {
                if (CollectionUtils.isEmpty(question.getAnswers())) {
                    LOGGER.warn("Question has no answers with number = {} and type {}",
                            question.getNumber(), question.getQuestionType());
                    continue;
                }
                answers.add(getUserAnswer(question));
            }
        }
        return answers;
    }

    private AnswerDto getUserAnswer(QuestionDto question) {
        askQuestion(String.format("%d. %s", question.getNumber(), question.getQuestion()));
        showAnswers(question.getAnswers());
        String userAnswer = getUserAnswer(question.getAnswers());
        return new AnswerDto(question.getNumber(), userAnswer);
    }

    private void askQuestion(String question) {
        System.out.println(question);
    }

    private void showAnswers(List<String> answers) {
        System.out.println("Ниже представлены варианты ответов: ");
        answers.forEach(answer ->
                System.out.println(String.format("\t-- %s", answer)));
    }

    private String getUserAnswer(Collection<String> answers) {
        String userAnswer;
        while (true) {
            userAnswer = consoleUtils.askUserAndGetNotBlankAnswer("Ответ: ", in);
            if (matchUserAnswer(answers, userAnswer)) {
                return userAnswer;
            } else {
                System.out.println("Нет совпадений, попробуйте еще раз!");
            }
        }
    }

    private boolean matchUserAnswer(Collection<String> answers, String userAnswer) {
        boolean result;
        for (String answer : answers) {
            result = userAnswer.toLowerCase().equals(answer.toLowerCase());
            if (result) {
                return true;
            }
        }
        return false;
    }
}
