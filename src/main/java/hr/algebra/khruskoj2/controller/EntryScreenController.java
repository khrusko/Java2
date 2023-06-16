package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.model.GameState;
import hr.algebra.khruskoj2.model.UserAnswer;
import hr.algebra.khruskoj2.rmiserver.ChatService;
import hr.algebra.khruskoj2.server.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static hr.algebra.khruskoj2.controller.GameScreenController.replayFilePath;

public class EntryScreenController {

    @FXML
    private Label btnStart;
    @FXML
    private Label btnSingleplayer;

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

    ChatService stub = null;

    public static String typeOfGame;

    private GameScreenController gameScreenController;
    private Client client;

    public void setGameScreenController(GameScreenController gameScreenController, Parent gameScreenRoot) {
        this.gameScreenController = gameScreenController;
        this.root = gameScreenRoot;
    }

    public void setGameScreenController(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;
    }

    @FXML
    void btnStartClick(MouseEvent event) throws RemoteException {
        typeOfGame = "multiplayer";
        stub.clearChatHistory();
        btnPause.setDisable(false);
        btnContinue.setDisable(true);
        EntryScreenController entryScreenController = new EntryScreenController();
        entryScreenController.setGameScreenController(gameScreenController);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/playerSelect.fxml"));
            Pane playerSelectPane = loader.load();

            PlayerSelectController playerSelectController = loader.getController();
            playerSelectController.setPMainContent(pMainContent);

            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(playerSelectPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSingleplayerClick(MouseEvent event) throws RemoteException {

        typeOfGame = "singleplayer";
        stub.clearChatHistory();
        btnPause.setDisable(false);
        btnContinue.setDisable(true);
        EntryScreenController entryScreenController = new EntryScreenController();
        entryScreenController.setGameScreenController(gameScreenController);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/playerSelect.fxml"));
            Pane playerSelectPane = loader.load();

            PlayerSelectController playerSelectController = loader.getController();
            playerSelectController.setPMainContent(pMainContent);

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
                System.out.println("Game state saved");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnLoadClick(MouseEvent event) throws RemoteException {
        if (gameScreenController != null) {
            stub.clearChatHistory();
            pMainContent.getChildren().clear();
            gameScreenController.setPMainContent(pMainContent);
            gameScreenController.loadGameState(pMainContent, root);
            System.out.println("Game state loaded");
        }
    }

    @FXML
    void btnReplayClick(MouseEvent event) {
        GameScreenController gameScreenController = GameScreenController.getInstance();
        GameReplayController gameReplayController = GameReplayController.getInstance();
        if (gameReplayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameReplay.fxml"));
                Parent rootReplay = loader.load();
                gameReplayController = loader.getController();
                gameReplayController.setRoot(rootReplay);

                List<UserAnswer> userAnswers = gameScreenController.loadFromXML(replayFilePath);

                gameReplayController.setUserAnswers(userAnswers);
                gameReplayController.replay(userAnswers, pMainContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnDocumentationClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/documentation.fxml"));
            Pane documentationPane = loader.load();

            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(documentationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnLeaderboardClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/leaderboards.fxml"));
            Pane leaderboardsPane = loader.load();

            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(leaderboardsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnExitClick(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void initialize() throws RemoteException, NotBoundException {
        lblPaused.setVisible(false);
        btnContinue.setDisable(true);
        btnPause.setDisable(true);
        Registry registry = LocateRegistry.getRegistry("localhost", 1919);
        stub = (ChatService) registry.lookup("hr.algebra.khruskoj2.rmiserver");
    }
}
