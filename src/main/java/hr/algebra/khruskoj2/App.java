package hr.algebra.khruskoj2;

import hr.algebra.khruskoj2.controller.EntryScreenController;
import hr.algebra.khruskoj2.controller.GameScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the entry screen FXML file
        FXMLLoader entryScreenLoader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/entryScreen.fxml"));
        Parent root = entryScreenLoader.load();
        EntryScreenController entryScreenController = entryScreenLoader.getController();

        // Load the game screen FXML file
        FXMLLoader gameScreenLoader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameScreen.fxml"));
        Parent gameScreenRoot = gameScreenLoader.load();
        GameScreenController gameScreenController = gameScreenLoader.getController();

        // Set the gameScreenController in entryScreenController
        entryScreenController.setGameScreenController(gameScreenController, gameScreenRoot);

        // Set the title and scene for the primary stage
        primaryStage.setTitle("Entry Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
