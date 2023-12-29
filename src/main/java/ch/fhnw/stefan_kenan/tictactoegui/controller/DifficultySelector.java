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

public class DifficultySelector implements Initializable, ConnectionStatusListener {

    private final Logger logger = LogManager.getLogger(DifficultySelector.class);
    @FXML
    public Button easyButton;
    @FXML
    public Button hardButton;
    @FXML
    public Button mediumButton;
    @FXML
    public Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NetworkHandler.getInstance().setStatusListener(this);
    }


    public void easyButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(1);
            disableDifficultyButtons();
        }
    }

    @FXML
    public void mediumButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(2);
            disableDifficultyButtons();
        }
    }

    @FXML
    public void hardButtonClicked() throws Exception {
        if(!GameController.getInstance().isGameRunning()){
            GameController.getInstance().createGame(3);
            disableDifficultyButtons();
        }
    }

    @FXML
    public void resetButtonClicked() throws Exception {
            enableDifficultyButtons();
            GameController.getInstance().resetGame();
    }

    private void disableDifficultyButtons(){
        easyButton.setDisable(true);
        mediumButton.setDisable(true);
        hardButton.setDisable(true);
        resetButton.setDisable(false);
    }

    private void enableDifficultyButtons(){
        easyButton.setDisable(false);
        mediumButton.setDisable(false);
        hardButton.setDisable(false);
        resetButton.setDisable(true);
    }

    private void disableAll(){
        disableDifficultyButtons();
        resetButton.setDisable(true);
    }

    @Override
    public void onConnected() {
        enableDifficultyButtons();
    }

    @Override
    public void onDisconnected() {
        disableAll();
    }
}
