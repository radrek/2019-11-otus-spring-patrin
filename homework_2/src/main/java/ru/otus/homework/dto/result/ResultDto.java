package ru.otus.homework.dto.result;

import java.util.List;

public class ResultDto {
    private List<QuestionResult> questionResults;
    private int passingScore;
    private int score;

    public ResultDto() {
        score = 0;
    }

    public List<QuestionResult> getQuestionResults() {
        return questionResults;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public int getScore() {
        return score;
    }

    public void setQuestionResults(List<QuestionResult> questionResults) {
        this.questionResults = questionResults;
    }

    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
