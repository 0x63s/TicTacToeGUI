package ch.fhnw.stefan_kenan.tictactoegui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameBoard {

    private Button[][] board = new Button[3][3];

    @FXML
    private Button cell00; // Represents the top-left cell

    @FXML
    private Button cell01;

    @FXML
    private Button cell02;

    @FXML
    private Button cell10;

    @FXML
    private Button cell11;

    @FXML
    private Button cell12;

    @FXML
    private Button cell20;

    @FXML
    private Button cell21;

    @FXML
    private Button cell22;

    // add all buttons to the board
    public void initialize() {
        board[0][0] = cell00;
        board[0][1] = cell01;
        board[0][2] = cell02;
        board[1][0] = cell10;
        board[1][1] = cell11;
        board[1][2] = cell12;
        board[2][0] = cell20;
        board[2][1] = cell21;
        board[2][2] = cell22;
    }
    public void handleClick(int row, int col) {

        // set button text to "X" in the font arial bald 45 in the color black centered in the button
        board[row][col].setText("X");
        board[row][col].setFont(javafx.scene.text.Font.font("Arial Bold", 45));
        board[row][col].setTextFill(javafx.scene.paint.Color.BLACK);
        board[row][col].setAlignment(javafx.geometry.Pos.CENTER);
    }
    public String getButtonText(int row, int col) {
        return board[row][col].getText();
    }
    public void resetBoard() {
        for (Button[] row : board) {
            for (Button button : row) {
                button.setText(" ");
            }
        }
    }

}
