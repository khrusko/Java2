package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.LeaderboardData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.CompletableFuture;

public class ResultScreenController {

    @FXML
    private Label lblCorrectAnswers;

    @FXML
    private Label lblWrongAnswers;

    private LeaderboardData leaderboardData;

    public void setResultData(int correctAnswers, int wrongAnswers, String player1Name, String player2Name) {
        lblCorrectAnswers.setText(String.valueOf(correctAnswers));
        lblWrongAnswers.setText(String.valueOf(wrongAnswers));

        leaderboardData = LeaderboardData.getInstance();

        if (leaderboardData != null) {
            int score = correctAnswers * 10;
            LeaderboardsController.LeaderboardEntry newEntry = new LeaderboardsController.LeaderboardEntry(player1Name, score);
            CompletableFuture.runAsync(() -> leaderboardData.addLeaderboardEntry(newEntry));
        } else {
            System.out.println("LeaderboardData instance is not set.");
        }
    }
}
