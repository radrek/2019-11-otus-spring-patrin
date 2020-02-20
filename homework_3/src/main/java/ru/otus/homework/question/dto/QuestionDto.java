package ru.otus.homework.question.dto;

import ru.otus.homework.question.MultipleChoiceQuestion;
import ru.otus.homework.question.Question;
import ru.otus.homework.question.additional.QuestionType;

import java.util.List;

public class QuestionDto {
    private final int number;
    private final String question;
    private final List<String> answers;
    private final QuestionType questionType;

    public QuestionDto(Question question) {
        this.number = question.getNumber();
        this.question = question.getQuestion();
        this.questionType = question.getType();
        if (QuestionType.MULTIPLE_CHOICE.equals(questionType)) {
            this.answers = ((MultipleChoiceQuestion) question).getAnswers();
        } else {
            this.answers = null;
        }
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "number=" + number +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", questionType=" + questionType +
                '}';
    }
}
