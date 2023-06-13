package hr.algebra.khruskoj2.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private int questionIndex;
    private String question;
    private List<String> wrongAnswers;
    private String correctAnswer;

    public Question(int questionIndex, String question, List<String> wrongAnswers, String correctAnswer) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.wrongAnswers = wrongAnswers;
        this.correctAnswer = correctAnswer;
    }

    // Getters and setters

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
