package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoard {
    private final Logger logger = LogManager.getLogger(GameBoard.class);

    public static Button[][] board = new Button[3][3];

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

        formatButtons();
    }

    //create a function that formats the buttons to have a centered text with 40pt font
    public void formatButtons() {
        String style = "-fx-font-size: 40pt;" +
                "-fx-background-color: #f0f0f0;" +
                "-fx-text-fill: #333333;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 10px;" +
                "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);";

        for (Button[] row : board) {
            for (Button button : row) {
                button.setStyle(style);
            }
        }
    }

    public static void updateTicTacToeCell(int row, int col, int value) {
        if(value == 1) {
            board[row][col].setText("X");
        } else if(value == -1) {
            board[row][col].setText("O");
        } else if(value == 0){
            board[row][col].setText(" ");
        } else {
            //Invalid Cell
        }
    }



    @FXML
    private void handleCell(ActionEvent event) {
        if(!GameController.getInstance().isGameRunning()) {
            logger.debug("Game not running");
            return;
        }

        if(!User.isUserCreated()){
            logger.debug("User not logged in");
            return;
        }


        Node source = (Node) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        // Handle the cell action here
        // colIndex and rowIndex might be null if not set, default to 0 if so
        int col = colIndex != null ? colIndex : 0;
        int row = rowIndex != null ? rowIndex : 0;

        // Now you can use row and col
        logger.debug("Cell clicked: " + row + " " + col);

        try{
            GameController.getInstance().makeMove(row, col);
        } catch (Exception e){
            logger.error("Error while making move: " + e.getMessage());
        }


    }

    public static String getCellValue(int row, int col) {
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
