package ru.otus.homework.ui.stage.question;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.homework.controller.question.QuestionController;
import ru.otus.homework.dto.AnswerDto;
import ru.otus.homework.dto.QuestionDto;
import ru.otus.homework.ui.interlocutor.Mediator;
import ru.otus.homework.ui.stage.question.additional.QuestionType;
import ru.otus.homework.ui.util.LocaleUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class QuestionStageImpl implements QuestionStage {
    private static final Logger LOGGER = LogManager.getLogger(QuestionStageImpl.class);

    private final QuestionController questionController;
    private final Mediator mediator;
    private final LocaleUtils localeUtils;

    public QuestionStageImpl(QuestionController questionController, Mediator mediator, LocaleUtils localeUtils) {
        this.questionController = questionController;
        this.mediator = mediator;
        this.localeUtils = localeUtils;
    }

    @Override
    public List<AnswerDto> askQuestions() {
        LOGGER.info("Ask questions");
        List<QuestionDto> questions = questionController.getQuestions(localeUtils.getUserLocale());
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
        mediator.askWithoutAnswer("question.ask", question.getNumber(), question.getQuestion());
        showAnswers(question.getAnswers());
        String userAnswer = getUserAnswer(question.getAnswers());
        return new AnswerDto(question.getNumber(), userAnswer);
    }

    private void showAnswers(List<String> answers) {
        mediator.say("question.answers.message");
        answers.forEach(answer ->
                mediator.say("question.answers.options", answer));
    }

    private String getUserAnswer(Collection<String> answers) {
        String userAnswer;
        while (true) {
            userAnswer = mediator.keepAskingUntilGetAnswer("question.answers.answer");
            if (matchUserAnswer(answers, userAnswer)) {
                return userAnswer;
            } else {
                mediator.say("question.answers.error");
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
