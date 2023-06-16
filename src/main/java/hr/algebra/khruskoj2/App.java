package hr.algebra.khruskoj2;

import hr.algebra.khruskoj2.controller.EntryScreenController;
import hr.algebra.khruskoj2.controller.GameScreenController;
import hr.algebra.khruskoj2.rmiserver.RmiServer;
import hr.algebra.khruskoj2.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Server server = Server.getInstance();
        if (!server.isRunning()) {
            new Thread(server::startServer).start();
        }

        FXMLLoader entryScreenLoader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/entryScreen.fxml"));
        Parent root = entryScreenLoader.load();
        EntryScreenController entryScreenController = entryScreenLoader.getController();

        FXMLLoader gameScreenLoader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameScreen.fxml"));
        Parent gameScreenRoot = gameScreenLoader.load();
        GameScreenController gameScreenController = gameScreenLoader.getController();

        entryScreenController.setGameScreenController(gameScreenController, gameScreenRoot);

        primaryStage.setTitle("Entry Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
