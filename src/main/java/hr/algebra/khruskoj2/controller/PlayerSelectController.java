package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.rmiserver.ChatService;
import hr.algebra.khruskoj2.server.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Executors;


public class PlayerSelectController {

    @FXML
    private Label btnStart;

    @FXML
    private Label lblInformation;
    @FXML
    private TextField tfPlayer1Name;

    private Pane pMainContent;

    public void setPMainContent(Pane pMainContent) {
        this.pMainContent = pMainContent;
    }

    private String player1Name;
    private Client client;


    @FXML
    public void btnStartClick(MouseEvent event) {

        player1Name = tfPlayer1Name.getText();

        if (player1Name.isEmpty()) {
            showAlert("Please enter your nickname.");
            return;
        }

        if (EntryScreenController.typeOfGame == "singleplayer") {
            loadGameScreen();
        } else if (EntryScreenController.typeOfGame == "multiplayer") {
            startClient();

        }

    }

    private void startClient() {
        btnStart.setDisable(true);
        btnStart.setText("Waiting for second player");
        btnStart.setLayoutX(160);
        new Thread(() -> {
            try {
                System.out.println("Starting the client...");
                client = new Client();
                client.connectToServer(() -> {
                    loadGameScreen();
                });
                System.out.println("Client started.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void loadGameScreen() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameScreen.fxml"));
                Parent root = loader.load();

                GameScreenController gameScreenController = loader.getController();
                gameScreenController.setPMainContent(pMainContent);
                gameScreenController.setPlayerNames(player1Name, player1Name);
                if(client!=null)
                gameScreenController.setClient(client);
                pMainContent.getChildren().clear();
                pMainContent.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void initialize()  {
        btnStart.setDisable(false);
        btnStart.setText("Start the game");
        if (EntryScreenController.typeOfGame == "singleplayer") {
            lblInformation.setText("Singleplayer - Player information");
        } else if (EntryScreenController.typeOfGame == "multiplayer") {
            lblInformation.setText("Multiplayer - Player information");
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