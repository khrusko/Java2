package hr.algebra.khruskoj2.model;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Question> questions;
    private int currentQuestionIndex;
    private List<UserAnswer> userAnswers;

    public GameState(int currentQuestionIndex, List<UserAnswer> userAnswers) {
        this.currentQuestionIndex = currentQuestionIndex;
        this.userAnswers = userAnswers;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public int getNumberOfWrongAnswersUser(String playerName) {
        int count = 0;
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.getPlayerName().equals(playerName) && !userAnswer.getSelectedAnswer().equals(userAnswer.getCorrectAnswer())) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfCorrectAnswersUser(String playerName) {
        int count = 0;
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.getPlayerName().equals(playerName) && userAnswer.getSelectedAnswer().equals(userAnswer.getCorrectAnswer())) {
                count++;
            }
        }
        return count;
    }
}
