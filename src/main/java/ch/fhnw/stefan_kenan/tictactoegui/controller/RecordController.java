package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordController implements Initializable {

    @FXML
    private Label winsLabel;
    @FXML
    private Label drawsLabel;
    @FXML
    private Label lossesLabel;

    private static RecordController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.winsLabel.setText("0");
        this.drawsLabel.setText("0");
        this.lossesLabel.setText("0");
    }

    public static RecordController getInstance() {
        if (instance == null) {
            instance = new RecordController();
        }
        return instance;
    }

    public void updateRecord() {

         this.winsLabel.setText(String.valueOf(User.getInstance().getWins()));
         this.drawsLabel.setText(String.valueOf(User.getInstance().getDraws()));
         this.lossesLabel.setText(String.valueOf(User.getInstance().getLosses()));
    }

}
