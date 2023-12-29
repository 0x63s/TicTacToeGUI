package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class GameController {

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
            //TODO: Create game object
        }



    }


    //We need to implemment the move function
    public void makeMove(int x, int y) throws Exception {
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

        User.getInstance().getToken();
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", User.getInstance().getToken());
        requestBody.put("row", x);
        requestBody.put("col", y);

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

            //TODO: Update gameLogic
            //TODO: Update Board

        }
    }


}
