package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.LeaderboardData;
import hr.algebra.khruskoj2.model.UserAnswer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class ResultScreenController {

    @FXML
    private Label lblCorrectAnswers;

    @FXML
    private Label lblWrongAnswers;

    @FXML
    private ListView<String> lvDetailedResults;
    private static ResultScreenController instance;

    public static ResultScreenController getInstance() {
        if (instance == null) {
            instance = new ResultScreenController();
        }
        return instance;
    }

    public void setResultData(int correctAnswers, int wrongAnswers, String player1Name, String player2Name, List<UserAnswer> userAnswers) {
        lvDetailedResults.getItems().clear();

        lblCorrectAnswers.setText(String.valueOf(correctAnswers));
        lblWrongAnswers.setText(String.valueOf(wrongAnswers));

        for (UserAnswer userAnswer : userAnswers) {
            String wronganswer1 = userAnswer.getWrongAnswers().get(0);
            String wronganswer2 = userAnswer.getWrongAnswers().get(1);
            String wronganswer3 = userAnswer.getWrongAnswers().get(2);
            String correctAnswer = userAnswer.getCorrectAnswer();
            String questionText = userAnswer.getQuestionText();
            String questionIndex = String.valueOf(userAnswer.getQuestionIndex());
            String selectedAnswer = userAnswer.getSelectedAnswer();
            String question = questionIndex + ". " + questionText + "\n";

            String answer1 = String.format("❌ A) %-30s %s \n", wronganswer1, wronganswer1.equals(selectedAnswer) ? "(Your Answer)" : "");
            String answer2 = String.format("❌ B) %-30s %s \n", wronganswer2, wronganswer2.equals(selectedAnswer) ? "(Your Answer)" : "");
            String answer3 = String.format("❌ C) %-30s %s \n", wronganswer3, wronganswer3.equals(selectedAnswer) ? "(Your Answer)" : "");
            String answer4 = String.format("✅ D) %-30s %s \n", correctAnswer, correctAnswer.equals(selectedAnswer) ? "(Your Answer)" : "");

            String blank = " ";

            lvDetailedResults.getItems().add(question);
            lvDetailedResults.getItems().add(answer1);
            lvDetailedResults.getItems().add(answer2);
            lvDetailedResults.getItems().add(answer3);
            lvDetailedResults.getItems().add(answer4);
            lvDetailedResults.getItems().add(blank);
        }

        // Check if player names are not empty or null before updating the leaderboard
        if (player1Name != null && !player1Name.isEmpty()) {
            updateLeaderboard(player1Name, correctAnswers);
        }

        if (player2Name != null && !player2Name.isEmpty()) {
            updateLeaderboard(player2Name, correctAnswers);
        }
    }

    public void updateLeaderboard(String player, int score) {
        // Create a new leaderboard entry
        LeaderboardsController.LeaderboardEntry newEntry = new LeaderboardsController.LeaderboardEntry(player, score*10);

        // Add the new entry to the leaderboard data
        LeaderboardData.getInstance().getLeaderboardEntries().add(newEntry);
    }
}
