package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.model.EntryScreenState;
import hr.algebra.khruskoj2.model.GameState;
import hr.algebra.khruskoj2.model.Question;
import hr.algebra.khruskoj2.model.UserAnswer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class EntryScreenController {

    @FXML
    private Label btnStart;

    @FXML
    private Label btnExit;

    @FXML
    private Label btnPause;

    @FXML
    private Label btnContinue;

    @FXML
    private Label btnSave;

    @FXML
    private Label btnLoad;

    @FXML
    private Label btnReplay;

    @FXML
    private Label btnDocumentation;

    @FXML
    private Label btnLeaderboard;

    @FXML
    public Pane pMainContent;

    @FXML
    private Label lblPaused;

    private Parent root;


    private GameScreenController gameScreenController;

    public void setGameScreenController(GameScreenController gameScreenController, Parent gameScreenRoot) {
        this.gameScreenController = gameScreenController;
        this.root = gameScreenRoot;
    }
    public void setGameScreenController(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;

    }

    @FXML
    void btnStartClick(MouseEvent event) {
        btnStart.setText("Restart");
        btnStart.setLayoutX(73.0);
        btnPause.setDisable(false);
        btnContinue.setDisable(true);
        EntryScreenController entryScreenController = new EntryScreenController();
        entryScreenController.setGameScreenController(gameScreenController);

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
    void btnPauseClick(MouseEvent event) {
        btnContinue.setDisable(false);
        btnPause.setDisable(true);
        lblPaused.setVisible(true);
        lblPaused.toFront();
        pMainContent.setDisable(true);
    }

    @FXML
    void btnContinueClick(MouseEvent event) {
        btnContinue.setDisable(true);
        btnPause.setDisable(false);
        lblPaused.setVisible(false);
        pMainContent.setDisable(false);
    }

    @FXML
    void btnSaveClick(MouseEvent event) {
        GameScreenController gameScreenController = GameScreenController.getInstance();
        if (gameScreenController != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream("gameSaveState.ser");
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

                List<UserAnswer> userAnswers = gameScreenController.getUserAnswers();
                int currentQuestionIndex = gameScreenController.getCurrentQuestionIndex();
                GameState gameState = new GameState(currentQuestionIndex, userAnswers);
                objectOutputStream.writeObject(gameState);
                System.out.println("Game state saved.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void btnLoadClick(MouseEvent event) {
        if (gameScreenController != null) {
            pMainContent.getChildren().clear();
            gameScreenController.setPMainContent(pMainContent);
            gameScreenController.loadGameState(pMainContent, root);
        }
    }

    @FXML
    void btnReplayClick(MouseEvent event) {
        GameScreenController gameScreenController = GameScreenController.getInstance();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/GameReplay.fxml"));
            Parent rootReplay = loader.load();

            GameReplayController gameReplayController = loader.getController();
            List<UserAnswer> userAnswers = gameScreenController.getUserAnswers();
            gameReplayController.replay(userAnswers);

            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(rootReplay);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    void btnExitClick(MouseEvent event) {
        // Close the entire application
        System.exit(0);
    }

    @FXML
    public void initialize() {
        lblPaused.setVisible(false);
        btnContinue.setDisable(true);
        btnPause.setDisable(true);
    }

}
