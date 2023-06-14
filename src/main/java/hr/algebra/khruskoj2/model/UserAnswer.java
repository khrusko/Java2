package hr.algebra.khruskoj2.model;

import java.io.Serializable;
import java.util.List;

public class UserAnswer implements Serializable {
    private int questionIndex;
    private String playerName;
    private String selectedAnswer;
    private String correctAnswer;
    private String questionText;
    private List<String> wrongAnswers;

    public UserAnswer(Question question, String selectedAnswer, String playerName) {
        this.questionIndex = question.getQuestionIndex();
        this.questionText = question.getQuestion();
        this.wrongAnswers = question.getWrongAnswers();
        this.correctAnswer = question.getCorrectAnswer();
        this.playerName = playerName;
        this.selectedAnswer = selectedAnswer;

    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }


    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

}
