package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameController {


    private int[][] board = new int[3][3];

    private final Logger logger = LogManager.getLogger(GameController.class);
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


    public void createGame(int difficulty) throws Exception {

        if(isGameRunning){
            logger.debug("Game is already running");
            return;
        }
        if(isGameFinished){
            logger.debug("Game is finished, resetting board");
            resetGame();
        }

        /*
            Post request to /game/new
            {
                "token":"64881692cd5fd400",
                "gameType":"TicTacToe",
                "difficulty" :"1"
            }

            token -> from User Object
            gameType -> TicTacToe
            difficulty -> from difficulty int

         */

        //get user instance
        User user = User.getInstance();

        logger.debug("Sending following data to server: " + user.getToken() + " " + "TicTacToe" + " " + difficulty);

        JSONObject requestBody = new JSONObject();
        requestBody.put("token", user.getToken());
        requestBody.put("gameType", "TicTacToe");
        requestBody.put("difficulty", difficulty);

        JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.createGameUrl, requestBody.toString());

                /*
                Receives a JSON object containing the following fields:
                {
                    "board" : [ [0, 0, 0],
                                [0, 0, 0],
                                [0, 0, 0]
                              ],
                    "difficulty" : 1,
                    "gameType" : "TicTacToe",
                    "options" : null,
                    "result" : false,
                    "token" : "64881692cd5fd400"
                }

                 */

        if(response == null){
            logger.error("Response is null");
            return;
        } else{
            logger.debug("Response: " + response.toString());
            isGameRunning = true;

            parseJsonBoard(response);
            isPlayerTurn = true;

            parseJsonBoard(response);
            //TODO: Create game object
            //TODO: CLear UI
        }



    }



    //We need to implemment the move function
    public void makeMove(int x, int y) throws Exception {
        if(!isGameRunning){
            logger.debug("No game is currently running");
            return;
        }
        if(isGameFinished){
            logger.debug("Game is already finished");
            return;
        }
        if(!isPlayerTurn){
            logger.debug("It is not the players turn");
            return;
        }

        /*

        Post request to /game/move
        {
            "token":"64881692cd5fd400",
            "row":"1",
            "col" :"2"

            token -> User.getInstance().getToken()
            row -> x
            col -> y
        }

         */

        //cast int to string
        String row = Integer.toString(x);
        String col = Integer.toString(y);

        User.getInstance().getToken();
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", User.getInstance().getToken());
        requestBody.put("row", row);
        requestBody.put("col", col);

        logger.debug("Sending following data to server: " + requestBody.toString());

        JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.makeMoveUrl, requestBody.toString());

        /*
            Response:

            {
                "board" : [ [0, -1,  0],
                            [0,  0,  1],
                            [0,  0,  0] ],
                "difficulty" : 1,
                "gameType" : "TicTacToe",
                "options" : null,
                "result" : false,
                "token" : "64881692cd5fd400"
            }
         */

        if(response == null){
            logger.error("Response is null");
            return;
        } else{
            logger.debug("Response: " + response.toString());

            parseJsonBoard(response);
            checkBoardWin();
            isPlayerTurn = true;
            //TODO: Update gameLogic
            //TODO: Update Board

        }
    }

    public void resetBoard(){
        board = new int[3][3];
        updateAllCells();
        isGameRunning = false;
        isGameFinished = false;
        isPlayerTurn = false;
        //TODO: Enable Difficulty Buttons when the connection is still active
    }

    public void quitGame() throws Exception {

        if (!isGameRunning) {
            logger.debug("No game is currently running.");
            return;
        }

        logger.debug("Quitting the game.");

        /*

            Post request to /game/quit
            {
                "token":"64881692cd5fd400"
            }

         */
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", User.getInstance().getToken());

        // Send the POST request to quit the game
        JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.quitGameUrl, requestBody.toString());

        /*

        Response:
            {
                "board" : [ [0, 0, 0],
                            [0, 0, 0],
                            [0, 0, 0] ],
                "difficulty" : 1,
                "gameType" : "TicTacToe",
                "options" : null,
                "result" : false,
                "token" : "64881692cd5fd400"
            }

         */
        if (response == null) {
            logger.error("Failed to quit the game: No response from server.");
        } else {
            logger.debug("Game quit successfully: " + response);


            // Update the game state
            isGameRunning = false;
            isGameFinished = true;
            isPlayerTurn = false;

            //TODO: Update UI
        }
    }

    // Method to parse the JSON board and update the int[][] board
    public void parseJsonBoard(JSONObject response) {
        // Check if the response has a 'board' key
        if (response.has("board")) {
            JSONArray jsonBoard = response.getJSONArray("board");

            // Iterate through the rows of the board
            for (int i = 0; i < jsonBoard.length(); i++) {
                JSONArray row = jsonBoard.getJSONArray(i);

                // Iterate through the columns of the current row
                for (int j = 0; j < row.length(); j++) {
                    // Get the value and update the int[][] board
                    board[i][j] = row.getInt(j);
                }
            }
            updateAllCells();
        } else {
            logger.error("Response does not contain 'board'");
        }
    }

    private void updateAllCells() {
        //cycle through each cell and update the state by using GameBoard.updateTicTacToeCell
        //print board for debugging
        logger.debug("Current board state: ");
        for(int i = 0; i < board.length; i++) {
            logger.debug(board[i][0] + " " + board[i][1] + " " + board[i][2]);
        }
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                //logger.debug("Updating cell: " + i + " " + j + " " + board[i][j]);
                GameBoard.updateTicTacToeCell(i, j, board[i][j]);
            }
        }
    }

    public void resetGame(){
        try{
            if(isGameRunning){
                quitGame();
            }
            isGameFinished = false;
            isGameRunning = false;
            isPlayerTurn = false;

            //reset UI
            board = new int[3][3];
            updateAllCells();
        } catch (Exception e){
            logger.error("Error while resetting game: " + e.getMessage());
        }

    }

    public void checkBoardWin(){
        //Search for tictactoe patterns in the board that will result a win
        //If found, call winGame()

        //Check rows
        for(int i = 0; i < board.length; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
                if(board[i][0] == 1){
                    winGame();
                } else if(board[i][0] == -1){
                    loseGame();
                }
            }
        }

        //Check columns
        for(int i = 0; i < board.length; i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i]){
                if(board[0][i] == 1){
                    winGame();
                } else if(board[0][i] == -1){
                    loseGame();
                }
            }
        }

        //Check diagonals
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
            if(board[0][0] == 1){
                winGame();
            } else if(board[0][0] == -1){
                loseGame();
            }
        }
        //check other diagonal
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
            if(board[0][2] == 1){
                winGame();
            } else if(board[0][2] == -1){
                loseGame();
            }
        }

        //if all cells are filled and no win condition is found, call drawGame()
        boolean isBoardFull = true;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == 0){
                    isBoardFull = false;
                }
            }
        }

        if(isBoardFull){
            drawGame();
        }
    }

    private void winGame(){
        logger.debug("Player won the game");
        isGameFinished = true;
        isGameRunning = false;
        isPlayerTurn = false;
        User.getInstance().addWin();
        RecordController.updateRecord();
        logger.debug("Wins: " + User.getInstance().getWins());
        //TODO: Update Stat UI
    }

    private void loseGame(){
        logger.debug("Player lost the game");
        isGameFinished = true;
        isGameRunning = false;
        isPlayerTurn = false;
        User.getInstance().addLoss();
        RecordController.updateRecord();

        //TODO: Update Stat UI
    }

    private void drawGame(){
        logger.debug("Game ended in a draw");
        isGameFinished = true;
        isGameRunning = false;
        isPlayerTurn = false;
        User.getInstance().addDraw();
        RecordController.updateRecord();

        //TODO: Update Stat UI
    }
}
