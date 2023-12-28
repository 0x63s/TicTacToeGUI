package ch.fhnw.stefan_kenan.tictactoegui.model;

import ch.fhnw.stefan_kenan.tictactoegui.controller.GameBoard;

public class GameLogic {

    private final GameBoard gameBoard = new GameBoard();

    private String currentPlayer;

    public GameLogic() {

        gameBoard.initialize();
        currentPlayer = "X";
    }


    public boolean makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            gameBoard.handleClick(row, col);
            switchPlayer();
            return true;
        }
        return false;
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && gameBoard.getButtonText(row, col) == " ";
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == "X") ? "O" : "X";
    }

    public void resetGame() {
        gameBoard.resetBoard();
        currentPlayer = "X";
    }


}
