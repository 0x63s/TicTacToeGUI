package ch.fhnw.stefan_kenan.tictactoegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LaunchApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage = stage;


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main.fxml")));

        primaryStage.setTitle("Tic Tac Toe GUI");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
