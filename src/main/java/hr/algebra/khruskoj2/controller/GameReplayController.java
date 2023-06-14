package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.model.UserAnswer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameReplayController {

    private static GameReplayController instance;
    @FXML
    private Label lblQuestion;
    @FXML
    private Label lblAnswer1;
    @FXML
    private Label lblAnswer2;
    @FXML
    private Label lblAnswer3;
    @FXML
    private Label lblAnswer4;
    @FXML
    private Label lblQuestionNumber;
    private List<UserAnswer> userAnswers;
    private Parent root;
    private int currentAnswerIndex;

    public static GameReplayController getInstance() {
        return instance;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void replay(List<UserAnswer> userAnswers, Pane pMainContent) {
        pMainContent.getChildren().clear();
        this.userAnswers = userAnswers;
        currentAnswerIndex = 0;
        replayNextAnswer(pMainContent);
        pMainContent.getChildren().add(root);
    }

    // Method to replay the next answer
    private void replayNextAnswer(Pane pMainContent) {
        String defaultStyle = "-fx-background-color: linear-gradient(#686868, #454545); -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";
        String correctStyle = "-fx-background-color: green; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";
        String incorrectStyle = "-fx-background-color: red; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";

        if (currentAnswerIndex < userAnswers.size()) {
            UserAnswer currentAnswer = userAnswers.get(currentAnswerIndex);
            List<String> wrongAnswers = currentAnswer.getWrongAnswers();
            String correctAnswer = currentAnswer.getCorrectAnswer();
            String selectedAnswer = currentAnswer.getSelectedAnswer();
            List<String> answerOptions = new ArrayList<>(wrongAnswers);
            answerOptions.add(correctAnswer);
            Collections.shuffle(answerOptions);
            String questionText = currentAnswer.getQuestionText();

            List<Label> labels = Arrays.asList(lblAnswer1, lblAnswer2, lblAnswer3, lblAnswer4);

            lblQuestion.setText(questionText);
            int size = Math.min(labels.size(), answerOptions.size());
            for (int i = 0; i < size; i++) {
                labels.get(i).setText(answerOptions.get(i));
                labels.get(i).setStyle(defaultStyle);
            }
            lblQuestionNumber.setText("Question " + (currentAnswerIndex + 1) + "/15");

            currentAnswerIndex++;

            PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
            pause1.setOnFinished(e -> {
                // Determine which label contains the correct and user answers, then apply styles
                for (Label label : labels) {
                    if (label.getText().equals(correctAnswer)) {
                        label.setStyle(correctStyle);
                    }
                    if (label.getText().equals(selectedAnswer)) {
                        label.setStyle(selectedAnswer.equals(correctAnswer) ? correctStyle : incorrectStyle);
                    }
                }

                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                pause2.setOnFinished(event -> {

                    for (Label label : labels) {
                        label.setStyle(defaultStyle);
                    }

                    replayNextAnswer(pMainContent);
                });
                pause2.play();
            });
            pause1.play();

        } else {
            //On complete ->
            lblQuestion.setText("Replay is over");
            lblAnswer1.setText("");
            lblAnswer2.setText("");
            lblAnswer3.setText("");
            lblAnswer4.setText("");
            lblQuestionNumber.setText("");
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                if (pMainContent.getChildren().contains(root)) {
                    pMainContent.getChildren().remove(root);
                }
            });
            pause.play();
        }
    }

}
