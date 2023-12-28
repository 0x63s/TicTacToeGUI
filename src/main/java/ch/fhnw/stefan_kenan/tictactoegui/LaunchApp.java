package ch.fhnw.stefan_kenan.tictactoegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LaunchApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();

        Parent connection = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Connection.fxml")));
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
        Parent difficultySelector = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DifficultySelector.fxml")));
        Parent gameField = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GameField.fxml")));

        root.getChildren().addAll(connection, login, difficultySelector, gameField);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
