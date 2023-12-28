package ch.fhnw.stefan_kenan.tictactoegui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Login {
    private final Logger logger = LogManager.getLogger(Login.class);

    @FXML
    private Button loginButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;


    public Login() {
    }


    public boolean login(String username, String password) {
        NetworkHandler networkHandler = NetworkHandler.getInstance();
            try {
                JSONObject loginJson = new JSONObject();
                loginJson.put("username", username);
                loginJson.put("password", password);

                JSONObject response = networkHandler.sendPostRequest(NetworkHandler.loginUrl, loginJson.toString());
                //Convert response to String
                String responseString = response.toString();
                logger.debug("Login response: " + responseString);

                // TODO: Process the response, e.g., check if login is successful

                logger.info("Login attempt for user: " + username);
                return true;
            } catch (Exception e) {
                logger.error("Login error: ", e);
                return false;
            }
    }

    @FXML
    public void onLoginButtonPressed(ActionEvent actionEvent) {
        logger.debug("Login button pressed");
    }

    @FXML
    public void onLogoutButtonPressed(ActionEvent actionEvent) {
        logger.debug("Logout button pressed");
    }
}

