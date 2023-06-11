package hr.algebra.khruskoj2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EntryScreenController {

    @FXML
    private Label btnStart;

    @FXML
    private Label btnExit;

    @FXML
    private Label btnDocumentation;

    @FXML
    private Label btnLeaderboard;

    @FXML
    public Pane pMainContent;

    @FXML
    void btnStartClick(MouseEvent event) {
        try {
            // Load the playerSelect.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/playerSelect.fxml"));
            Pane playerSelectPane = loader.load();

            // Get the controller of the playerSelect.fxml file
            PlayerSelectController playerSelectController = loader.getController();

            // Pass pMainContent to the PlayerSelectController
            playerSelectController.setPMainContent(pMainContent);

            // Set the loaded pane as the content of pMainContent
            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(playerSelectPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnContinueClick(MouseEvent event) {
        // TODO: Add Continue functionality
    }

    @FXML
    void btnPauseClick(MouseEvent event) {
        // TODO: Add Pause functionality
    }

    @FXML
    void btnExitClick(MouseEvent event) {
        // Close the entire application
        System.exit(0);
    }

    @FXML
    void btnDocumentationClick(MouseEvent event) {
        try {
            // Load the documentation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/documentation.fxml"));
            Pane documentationPane = loader.load();

            // Set the loaded pane as the content of pMainContent
            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(documentationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnLeaderboardClick(MouseEvent event) {
        try {
            // Load the leaderboards.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/leaderboards.fxml"));
            Pane leaderboardsPane = loader.load();

            // Set the loaded pane as the content of pMainContent
            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(leaderboardsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
