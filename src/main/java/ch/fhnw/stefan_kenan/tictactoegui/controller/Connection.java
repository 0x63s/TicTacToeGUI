package ch.fhnw.stefan_kenan.tictactoegui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class Connection implements Initializable, ConnectionStatusListener {
    private final Logger logger = LogManager.getLogger(Connection.class);
    @FXML
    private TextField serverIpField;

    @FXML
    private TextField portField;

    @FXML
    private Button pingButton;

    @FXML
    private Label pingStatusLabel;


    /*

    The purpose of this function is to ping the server to check if it is online,
    It modifies the ping button and the ping status label to show the user the status of the ping

     */
    @FXML
    public void onPingButtonPressed() {
        pingButton.setDisable(true);
        pingButton.setText("Pinging...");

        logger.debug("Ping button pressed");
        String serverIp = serverIpField.getText();

        logger.debug("Checking Domain format with regex - Domain: " + serverIp);


        final String urlRegex = "^(https?://)?(www\\.)?(([a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,6})|localhost|([a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+)|((\\d{1,3}\\.){3}\\d{1,3}))(\\/)?$";


        //regex that checks if the domain format is correct
        if(!serverIp.matches(urlRegex)){
            logger.error("Invalid domain format");
            pingButton.setText("Connect");
            pingButton.setDisable(false);
            pingStatusLabel.setText("Invalid domain format");
            return;
        }

        //remove last character if it is a slash
        if(serverIp.charAt(serverIp.length()-1) == '/'){
            serverIp = serverIp.substring(0, serverIp.length()-1);
        }


        String port = portField.getText();
        logger.debug("Setting server ip to: " + serverIp);
        logger.debug("Setting port to: " + port);
        NetworkHandler networkHandler = NetworkHandler.getInstance();
        networkHandler.setEndpointUrl(serverIp);
        networkHandler.setPort(port);
        logger.debug("Sending ping request");
        try {
            if(pingServer()){
                pingStatusLabel.setText("Connection successful");
                //NetworkHandler.getInstance().startPingTask();
            } else {
                pingStatusLabel.setText("Connection failed");
            }
            pingButton.setText("Connect");
            pingButton.setDisable(false);


        } catch (Exception e) {
            logger.error("Connection error: ", e);
            pingButton.setText("Connect");
            pingButton.setDisable(false);
            pingStatusLabel.setText("Connection failed");
        }
    }

    /*

    This function is used to ping the server, it returns true if the ping was successful and false if it failed

     */
    public boolean pingServer() throws Exception {
        logger.debug("Pinging server");

        NetworkHandler networkHandler = NetworkHandler.getInstance();
        JSONObject response = networkHandler.sendGetRequest(NetworkHandler.pingUrl);

        if(response == null){
            logger.debug("Ping failed");
            pingButton.setText("Connection failed");

            return false;
        } else{
            logger.debug("Ping response: " + response);
        }

        //response returns { "ping":"success" } or nothing if ping failed
        if(response.getString("ping").equals("success")){
            logger.info("Ping successful");
            pingButton.setText("Connection successful");
            return true;
        } else {
            logger.info("Ping failed");
            pingButton.setText("Connection failed");
            return false;
        }
    }

    /*

    This code is used to change the text from the text fields in Connection view.

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NetworkHandler.getInstance().addConnectionStatusListener(this);
        logger.debug("Initializing Connection view");
        serverIpField.setText(NetworkHandler.getEndpointUrl());
        portField.setText(NetworkHandler.getPort());

    }

    @Override
    public void onConnected() {
        Platform.runLater(() -> {
            logger.debug("Disabling Connection UI elements");
            serverIpField.setDisable(true);
            portField.setDisable(true);
            pingButton.setDisable(true);
            InfoController.getInstance().updateInfoLabel(4);
        });
    }

    @Override
    public void onDisconnected() {
        Platform.runLater(() -> {
            logger.debug("Enabling Connection UI elements");
            serverIpField.setDisable(false);
            portField.setDisable(false);
            pingButton.setDisable(false);
        });
    }


}
