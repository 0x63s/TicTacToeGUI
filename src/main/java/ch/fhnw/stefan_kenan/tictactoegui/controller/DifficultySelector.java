package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class DifficultySelector implements Initializable {

    private final Logger logger = LogManager.getLogger(DifficultySelector.class);
    @FXML
    public Button easyButton;
    @FXML
    public Button hardButton;
    @FXML
    public Button mediumButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

            //TODO: Create game object
        }



    }


    public void easyButtonClicked() throws Exception {
        createGame(1);
    }

    @FXML
    public void mediumButtonClicked() throws Exception {
        createGame(2);
    }

    @FXML
    public void hardButtonClicked() throws Exception {
        createGame(3);
    }

}
