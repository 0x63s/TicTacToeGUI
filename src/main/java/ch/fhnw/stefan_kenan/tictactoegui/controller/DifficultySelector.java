package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class DifficultySelector implements Initializable {

    private final Logger logger = LogManager.getLogger(DifficultySelector.class);
    @FXML
    public Button easyButton;
    @FXML
    public Button hardButton;
    @FXML
    public Button mediumButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void easyButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(1);
        }
    }

    @FXML
    public void mediumButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(2);
        }
    }

    @FXML
    public void hardButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(3);
        }
    }

    @FXML
    public void resetButtonClicked() throws Exception {
            GameController.getInstance().resetGame();
    }
}
