package hr.algebra.khruskoj2.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import hr.algebra.khruskoj2.controller.GameScreenController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerSelectController {

    @FXML
    private Label btnStart;

    @FXML
    private TextField tfPlayer1Name;

    @FXML
    private TextField tfPlayer2Name;

    private Pane pMainContent; // Reference to the pMainContent pane

    public void setPMainContent(Pane pMainContent) {
        this.pMainContent = pMainContent;
    }

    private String player1Name;
    private String player2Name;


    @FXML
    public void btnStartClick(MouseEvent event) {
        player1Name = tfPlayer1Name.getText();
        player2Name = tfPlayer2Name.getText();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            showAlert("Please enter both player names.");
            return; // Exit the method to prevent further processing
        }

        // Perform actions when the "Start the game" button is clicked
        // You can use the player names (player1Name and player2Name)
        // for further processing or game initialization

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameScreen.fxml"));
            Parent root = loader.load();

            // Get the controller of the game screen
            GameScreenController gameScreenController = loader.getController();

            GameScreenController.getInstance().setPMainContent(pMainContent);

            // Pass the player names to the game screen controller
            gameScreenController.setPlayerNames(player1Name, player2Name);

            // Set the loaded pane as the content of pMainContent
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