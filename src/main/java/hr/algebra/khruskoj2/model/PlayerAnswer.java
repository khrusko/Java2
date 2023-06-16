package hr.algebra.khruskoj2.model;

import java.io.Serializable;

public class PlayerAnswer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private String selectedAnswer;

    public PlayerAnswer(String playerName, String selectedAnswer) {
        this.playerName = playerName;
        this.selectedAnswer = selectedAnswer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
