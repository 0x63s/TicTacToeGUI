package ch.fhnw.stefan_kenan.tictactoegui.model;

import ch.fhnw.stefan_kenan.tictactoegui.controller.GameBoard;

import static ch.fhnw.stefan_kenan.tictactoegui.controller.GameBoard.getCellValue;

public class GameLogic {


    private String currentPlayer;

    private static GameLogic instance;

    public GameLogic() {
        currentPlayer = "X";
    }


    public boolean makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            //gameBoard.handleClick(row, col);
            switchPlayer();
            return true;
        }
        return false;
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && getCellValue(row, col) == " ";
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (checkLine(getCellValue(i, 0), getCellValue(i, 1), getCellValue(i, 2))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (checkLine(getCellValue(0, i), getCellValue(1, i), getCellValue(2, i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return checkLine(getCellValue(0, 0), getCellValue(1,1), getCellValue(2,2)) || checkLine(getCellValue(0, 2), getCellValue(1, 1), getCellValue(2, 0));
    }

    private boolean checkLine(String a, String b, String c) {
        return !a.equals(" ") && a.equals(b) && b.equals(c);
    }
    public boolean isBoardFull() {
        // Check if the board is full (indicating a draw)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (" ".equals(getCellValue(i, j))) {
                    return false; // Found an empty cell, board is not full
                }
            }
        }
        return true; // No empty cells, board is full
    }




}
