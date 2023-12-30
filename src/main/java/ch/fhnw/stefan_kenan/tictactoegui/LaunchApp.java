package ch.fhnw.stefan_kenan.tictactoegui;

import ch.fhnw.stefan_kenan.tictactoegui.controller.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LaunchApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        BorderPane borderPane = new BorderPane();


        Parent connection = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Connection.fxml")));
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
        Parent difficultySelector = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DifficultySelector.fxml")));
        Parent gameField = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GameBoard.fxml")));
        Parent playerRecord = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/PlayerRecord.fxml")));
        Parent infoLabel = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/InfoLabel.fxml")));

        vbox1.getChildren().addAll(difficultySelector, gameField);
        vbox2.getChildren().addAll(login, playerRecord);
        borderPane.setTop(connection);
        borderPane.setLeft(vbox2);
        borderPane.setCenter(vbox1);
        borderPane.setBottom(infoLabel);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
