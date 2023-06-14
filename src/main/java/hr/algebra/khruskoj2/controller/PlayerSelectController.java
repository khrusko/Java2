package hr.algebra.khruskoj2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PlayerSelectController {

    @FXML
    private Label btnStart;

    @FXML
    private TextField tfPlayer1Name;

    private Pane pMainContent;

    public void setPMainContent(Pane pMainContent) {
        this.pMainContent = pMainContent;
    }

    private String player1Name;


    @FXML
    public void btnStartClick(MouseEvent event) {
        player1Name = tfPlayer1Name.getText();

        if (player1Name.isEmpty()) {
            showAlert("Please enter your nickname.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameScreen.fxml"));
            Parent root = loader.load();

            GameScreenController gameScreenController = loader.getController();
            GameScreenController.getInstance().setPMainContent(pMainContent);

            //TODO Razlikovat 2 playera i ovdje ih setat
            gameScreenController.setPlayerNames(player1Name, player1Name);

            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}