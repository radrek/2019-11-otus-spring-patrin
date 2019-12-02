package ru.otus.homework.dto;

import ru.otus.homework.domain.question.MultipleChoiceQuestion;
import ru.otus.homework.domain.question.Question;
import ru.otus.homework.domain.question.QuestionType;

import java.util.List;

public class QuestionDto {
    private int number;
    private String question;
    private List<String> answers;
    private QuestionType questionType;

    public QuestionDto(Question question) {
        this.number = question.getNumber();
        this.question = question.getQuestion();
        this.questionType = question.getType();
        if (QuestionType.MULTIPLE_CHOICE.equals(questionType)) {
            this.answers = ((MultipleChoiceQuestion) question).getAnswers();
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
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
