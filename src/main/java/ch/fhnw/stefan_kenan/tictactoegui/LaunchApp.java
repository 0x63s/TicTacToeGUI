package ch.fhnw.stefan_kenan.tictactoegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LaunchApp extends Application {
    private static final Logger logger = LogManager.getLogger(LaunchApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.debug("Launching application...");
        logger.debug("Loading app.fxml...");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/app.fxml")));

        stage.setTitle("Online TicTacToe GUI");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

        logger.info("Application started");
    }
}
