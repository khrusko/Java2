package hr.algebra.khruskoj2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultScreenController {

    @FXML
    private Label lblCorrectAnswers;

    @FXML
    private Label lblWrongAnswers;

    public void setResultData(int correctAnswers, int wrongAnswers) {
        lblCorrectAnswers.setText(String.valueOf(correctAnswers));
        lblWrongAnswers.setText(String.valueOf(wrongAnswers));
    }


}
