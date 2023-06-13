package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.LeaderboardData;
import hr.algebra.khruskoj2.model.UserAnswer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ResultScreenController {

    @FXML
    private Label lblCorrectAnswers;

    @FXML
    private Label lblWrongAnswers;

    @FXML
    private ListView<String> lvDetailedResults;

    public void setResultData(int correctAnswers, int wrongAnswers, String player1Name, String player2Name, List<UserAnswer> userAnswers) {
        lblCorrectAnswers.setText(String.valueOf(correctAnswers));
        lblWrongAnswers.setText(String.valueOf(wrongAnswers));

        // Clear the ListView
        lvDetailedResults.getItems().clear();



        // Add each user answer to the ListView
        for (UserAnswer userAnswer : userAnswers) {
            String wronganswer1 = userAnswer.getWrongAnswers().get(0);
            String wronganswer2 = userAnswer.getWrongAnswers().get(1);
            String wronganswer3 = userAnswer.getWrongAnswers().get(2);
            String correctAnswer = userAnswer.getCorrectAnswer();
            String questionText = userAnswer.getQuestionText();
            String questionIndex = String.valueOf(userAnswer.getQuestionIndex());
            String selectedAnswer = userAnswer.getSelectedAnswer();
            String question = questionIndex + ". " + questionText+"\n";


// Create a formatted string for each answer with dynamic gaps
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
    }
}