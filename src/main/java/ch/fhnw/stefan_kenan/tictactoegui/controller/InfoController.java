package ch.fhnw.stefan_kenan.tictactoegui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

    @FXML
    private Label infoLabel;

    private Label welcomeLabel;

    private Label winLabel;

    private Label drawLabel;

    private Label lossLabel;

    private Label loginLabel;

    private Label logutLabel;

    private Label failedRegLabel;

    private Label selectDifficultyLabel;

    private Label easyLabel;

    private Label mediumLabel;

    private Label hardLabel;

    private Label failedLoginLabel;

    private Label successLoginLabel;



    private static Label[] displayLabel = new Label[1];

    private static Label[] infoLabels = new Label[13];

    private static InfoController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.infoLabel.setText("Welcome to Tic Tac Toe!");
        this.welcomeLabel = new Label("Welcome to Tic Tac Toe!");
        this.winLabel = new Label("You won!");
        this.drawLabel = new Label("It's a draw!");
        this.lossLabel = new Label("You lost!");
        this.loginLabel = new Label("Please login or register!");
        this.logutLabel = new Label("You have been logged out!");
        this.failedRegLabel = new Label("Registration failed... Account already exists!");
        this.selectDifficultyLabel = new Label("Please select a difficulty!");
        this.easyLabel = new Label("Easy");
        this.mediumLabel = new Label("Medium");
        this.hardLabel = new Label("Hard");
        this.failedLoginLabel = new Label("Login failed... Wrong username or password!");
        this.successLoginLabel = new Label("Login/Registration successful! - Please select a difficulty!");

        displayLabel[0] = infoLabel;

        infoLabels[0] = welcomeLabel;
        infoLabels[1] = winLabel;
        infoLabels[2] = drawLabel;
        infoLabels[3] = lossLabel;
        infoLabels[4] = loginLabel;
        infoLabels[5] = logutLabel;
        infoLabels[6] = failedRegLabel;
        infoLabels[7] = selectDifficultyLabel;
        infoLabels[8] = easyLabel;
        infoLabels[9] = mediumLabel;
        infoLabels[10] = hardLabel;
        infoLabels[11] = failedLoginLabel;
        infoLabels[12] = successLoginLabel;


    }

    public static InfoController getInstance() {
        if (instance == null) {
            instance = new InfoController();
        }
        return instance;
    }

    public void updateInfoLabel(int i) {
        displayLabel[0].setText(infoLabels[i].getText());
    }




}
