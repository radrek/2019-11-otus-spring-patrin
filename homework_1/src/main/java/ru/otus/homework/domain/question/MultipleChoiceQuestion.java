package ru.otus.homework.domain.question;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class MultipleChoiceQuestion implements Question {
    private static final QuestionType TYPE = QuestionType.MULTIPLE_CHOICE;

    @CsvBindByName
    private int number;

    @CsvBindByName
    private String question;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = "\\+\\+")
    private List<String> answers;

    @CsvBindByName(column = "CORRECT_ANSWER")
    private String correctAnswer;

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public QuestionType getType() {
        return TYPE;
    }

    @Override
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
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "number=" + number +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
