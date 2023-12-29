package ch.fhnw.stefan_kenan.tictactoegui.controller;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winsLabel.setText("0");
        drawsLabel.setText("0");
        lossesLabel.setText("0");
    }

    public void updateRecord(int wins, int draws, int losses) {
        this.winsLabel.setText(String.valueOf(wins));
        this.drawsLabel.setText(String.valueOf(draws));
        this.lossesLabel.setText(String.valueOf(losses));
    }

}

