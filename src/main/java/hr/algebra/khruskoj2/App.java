package hr.algebra.khruskoj2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the entry screen FXML file
        Parent root = FXMLLoader.load(getClass().getResource("entryScreen.fxml"));

        // Set the title and scene for the primary stage
        primaryStage.setTitle("Entry Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
