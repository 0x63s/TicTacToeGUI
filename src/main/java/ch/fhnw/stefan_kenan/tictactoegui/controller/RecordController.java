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

    //putting all labels in a static array for easier access
    public static Label[] labels = new Label[3];


    private static RecordController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.winsLabel.setText("0");
        this.drawsLabel.setText("0");
        this.lossesLabel.setText("0");
        labels[0] = winsLabel;
        labels[1] = drawsLabel;
        labels[2] = lossesLabel;
    }

    public static RecordController getInstance() {
        if (instance == null) {
            instance = new RecordController();
        }
        return instance;
    }

    public static void updateRecord() {
         labels[0].setText(String.valueOf(User.getInstance().getWins()));
         labels[1].setText(String.valueOf(User.getInstance().getDraws()));
         labels[2].setText(String.valueOf(User.getInstance().getLosses()));
    }

}
