package ru.otus.homework.ui;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.homework.controller.checker.CheckerController;
import ru.otus.homework.controller.question.QuestionController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.dto.ResultDto;
import ru.otus.homework.ui.questuion.QuestionType;
import ru.otus.homework.ui.user.User;
import ru.otus.homework.ui.util.ConsoleUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceImpl implements UserInterface {
    private static final Logger LOGGER = LogManager.getLogger(UserInterfaceImpl.class);

    private final QuestionController questionController;
    private final CheckerController checkerController;
    private final ConsoleUtils consoleUtils;
    private final Scanner in;

    public UserInterfaceImpl(QuestionController questionController, CheckerController checkerController,
                             ConsoleUtils consoleUtils) {
        this.questionController = questionController;
        this.checkerController = checkerController;
        this.consoleUtils = consoleUtils;
        this.in = new Scanner(System.in);
    }

    @Override
    public void start() {
        showGreeting();
        User user = getUser();
        List<QuestionDto> questions = questionController.getQuestions();
        List<AnswerDto> answers = getAnswers(questions);
        List<ResultDto> resultDtos = checkerController.checkAnswersOnCorrect(answers);
        System.out.println(
                String.format("%s %s ваши результаты ответов представлены ниже: ",
                        user.getFirstName(), user.getSecondName()));
        resultDtos
                .forEach(resultDto -> {
                    String result;
                    switch (resultDto.getStatus()) {
                        case OK:
                            result = "Правильно";
                            break;
                        case FAIL:
                            result = "Неправильно";
                            break;
                        case QUESTION_NOT_FOUND:
                        default:
                            result = "Вопрос не был найден";
                            break;
                    }
                    System.out.println(String.format("%d. %s", resultDto.getNumber(), result));
                });
    }

    private void showGreeting() {
        System.out.println("Добро пожаловать в опросник!");
    }

    private User getUser() {
        String firstName = getPartName("Имя");
        String secondName = getPartName("Фамилию");
        return new User(firstName, secondName);
    }

    private String getPartName(String part) {
        String partNameQuestion = String.format("Напишите %s: ", part);
        return consoleUtils.askUserAndGetNotBlankAnswer(partNameQuestion, in);
    }

    private List<AnswerDto> getAnswers(List<QuestionDto> questions) {
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
