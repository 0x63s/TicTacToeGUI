package ch.fhnw.stefan_kenan.tictactoegui.controller;

public class GameController {
    private static GameController instance;
    private boolean isPlayerTurn = false;
    private boolean isGameRunning = false;
    private boolean isGameFinished = false;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }





}
