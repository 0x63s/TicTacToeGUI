package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Login implements Initializable, ConnectionStatusInterface {
    private final Logger logger = LogManager.getLogger(Login.class);

    @FXML
    private Button loginButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    public void onRegisterButtonPressed() {
        logger.debug("Register button pressed, getting username and password");

        if(User.isUserCreated()){
            logger.debug("User already logged in");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();


        logger.debug("Checking if username and password are valid");
        //TODO: Check if username and password are valid

        logger.debug("Sending register request to server");
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", username);
        requestBody.put("password", password);

        try {
            JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.registerUrl, requestBody.toString());

            /* Upon successful registration, the server will respond with a JSON object containing the following fields:
               {
                "password" : "",
                "token" : null,
                "userExpiry" : "2022-10-25T12:18:14.252767673",
                "userName" : "Anna"
               }
            */
            logger.debug("Register response: " + response.toString());
            if(response.has("userName")) {
                logger.info("Registration successful");

                //I am adding auto login here, for a better UX
                logger.debug("!!! --- Auto login --- !!!");
                onLoginButtonPressed(null);

            } else {
                logger.error("Registration failed");
            }
        } catch (Exception e) {
            logger.error("Register error: ", e);
        }
    }

    @FXML
    public void onLoginButtonPressed(ActionEvent actionEvent) {
        logger.debug("Login button pressed");

        if(User.isUserCreated()){
            logger.debug("User already logged in");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        logger.debug("Checking if username and password are valid");
        //TODO: Check if username and password are valid


        /*
            The server will accept the following JSON object as a login request:
            {
                "userName" : "Anna",
                "password" : "anna"
            }
         */

        logger.debug("Sending login request to server");
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", username);
        requestBody.put("password", password);

        /*
            The server will respond with a different JSON objects containing the following fields:

            If the login was successful:
            {
                "password" : "",
                "token" : "64881692cd5fd400",
                "userExpiry" : "2022-10-25T12:28:18.481823789",
                "userName" : "Anna"
            }

            If the login was unsuccessful:
            {
                "error" : "User error",
                "error_description" : "Wrong password for user 'Anna'"
            }
         */

        try {
            JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.loginUrl, requestBody.toString());
            logger.debug("Login response: " + response.toString());

            if(response.has("token")) {
                logger.info("Login successful");
                passwordField.clear(); //Poof! Password is gone
                User.getInstance().createUser(
                        response.getString("userName"),
                        response.getString("token"),
                        LocalDateTime.parse(response.getString("userExpiry"))
                );
            } else {
                logger.error("Login failed");
            }
        } catch (Exception e) {
            logger.error("Login error: ", e);
        }
    }

    @FXML
    public void onLogoutButtonPressed(ActionEvent actionEvent) {
        logger.debug("Logout button pressed");

        if(!User.isUserCreated()){
            logger.debug("User not logged in");
            return;
        }

        /*
            The server will accept the following JSON object as a logout request:
            {
                "userName":"Anna"
            }
         */

        logger.debug("Sending logout request to server");
        JSONObject requestBody = new JSONObject();
        final String username = User.getInstance().getUserName();
        requestBody.put("userName", username);

        /*
            The server will respond with a different JSON objects containing the following fields:

            {
                "password" : "",
                "token" : null,
                "userExpiry" : null,
                "userName" : "Anna"
            }
         */

        try {
            JSONObject response = NetworkHandler.getInstance().sendPostRequest(NetworkHandler.logoutUrl, requestBody.toString());
            logger.debug("Logout response: " + response.toString());

            if(response.has("userName")) {
                logger.info("Logout successful");
                User.getInstance().clearUser();
            } else {
                logger.error("Logout failed");
            }
        } catch (Exception e) {
            logger.error("Logout error: ", e);
        }
    }

    /*

    The following methods are used to enable/disable the login/logout buttons when a connection is established/disconnected

     */
    @Override
    public void onConnected() {
        Platform.runLater(() -> {
            loginButton.setDisable(false);
            logoutButton.setDisable(false);
        });
    }

    @Override
    public void onDisconnected() {
        Platform.runLater(() -> {
            loginButton.setDisable(true);
            logoutButton.setDisable(true);
            registerButton.setDisable(true);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: Add listener
    }
}

