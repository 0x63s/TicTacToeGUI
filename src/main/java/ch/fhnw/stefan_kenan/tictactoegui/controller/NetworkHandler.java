package ch.fhnw.stefan_kenan.tictactoegui.controller;

import ch.fhnw.stefan_kenan.tictactoegui.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NetworkHandler {
    private static NetworkHandler instance;
    private boolean serverReachable = false;
    private final Logger logger = LogManager.getLogger(NetworkHandler.class);
    private static String endpointUrl = "http://localhost/";
    private static String port = "50005";
    public static final String pingUrl = "ping";
    public static final String registerUrl = "users/register";
    public static final String loginUrl = "users/login";
    public static final String logoutUrl = "users/logout";
    public static final String createGameUrl = "game/new";
    public static final String makeMoveUrl = "game/move";
    public static final String quitGameUrl = "game/quit";

    private ConnectionStatusListener statusListener;

    public void setStatusListener(ConnectionStatusListener listener) {
        this.statusListener = listener;
    }

    NetworkHandler(){
    }

    public void setEndpointUrl(String url) {
        endpointUrl = url;
    }

    public void setPort(String port) {
        NetworkHandler.port = port;
    }

    public static String getEndpointUrl() {
        return endpointUrl;
    }

    public static String getPort() {
        return port;
    }

    public static NetworkHandler getInstance() {
        if (instance == null) {
            instance = new NetworkHandler();
        }
        return instance;
    }


    public JSONObject sendGetRequest(String urlStr) throws Exception {
        if(!pingServer()) {
            logger.error("Server not reachable");
            return null;
        } else {



            String uri = endpointUrl + ":" + port + "/" + urlStr;

            logger.debug("Sending GET request to: " + uri);
            URL url = URI.create(uri).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return new JSONObject(content.toString());
            } catch(java.net.ConnectException e) {
                logger.error("[GET] Error: Connection refused!");
            } catch(Exception e) {
                logger.error("[GET] Error while processing Get Request!", e);
            } //Catch connection refuesed exception
            finally {
                logger.debug("Closing connection to: " + urlStr);
                con.disconnect();
            }
            return null;

        }
    }

    public JSONObject sendPostRequest(String urlStr, String body) throws Exception {
        if(!pingServer()) {
            logger.error("Server not reachable");
            return null;
        } else {

            final String uri = endpointUrl + ":" + port + "/" + urlStr;
            logger.debug("Sending POST request to: " + uri);
            URL url = URI.create(uri).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            con.setDoOutput(true);
            con.getOutputStream().write(body.getBytes());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return new JSONObject(content.toString());
            } catch (Exception e) {
                logger.error("Error while processing Post Request!", e);
            } finally {
                logger.debug("Closing connection to: " + urlStr);
                con.disconnect();
            }
            return null;
        }
    }


    /*
        This method is used to ping the server
     */
    private final List<ConnectionStatusListener> listeners = new ArrayList<>();


    public void addConnectionStatusListener(ConnectionStatusListener listener) {
        listeners.add(listener);
    }

    public void removeConnectionStatusListener(ConnectionStatusListener listener) {
        listeners.remove(listener);
    }

    private void notifyConnected() {
        for (ConnectionStatusListener listener : listeners) {
            listener.onConnected();
        }
    }

    private void notifyDisconnected() {
        for (ConnectionStatusListener listener : listeners) {
            listener.onDisconnected();
        }
    }

    public boolean pingServer() throws Exception {
        boolean oldServerReachable = serverReachable;
        logger.debug("Pinging server");



        String uri = endpointUrl + ":" + port + "/" + pingUrl;
        JSONObject response = null;

        logger.debug("Sending GET request to: " + uri);
        URL url = URI.create(uri).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            response = new JSONObject(content.toString());

            if(response == null){
                serverReachable = false;
                return false;
            }

            //response returns { "ping":"success" } or nothing if ping failed
        } catch(Exception e) {
            serverReachable = false;
            logger.error("[GET] Error: Connection refused!");
        }

        logger.debug("Closing connection to: " + pingUrl);
        con.disconnect();



        if(response != null && response.getString("ping").equals("success")){
            serverReachable = true;
            if(oldServerReachable != serverReachable) {
                logger.info("Ping successful - Server reachable - Notifying listeners");
                notifyConnected();
            }
            return true;
        } else {
            serverReachable = false;
            if(oldServerReachable != serverReachable){
                GameController.getInstance().resetBoard();
                User.getInstance().clearUser();
                notifyDisconnected();
                logger.debug("Ping failed - Server not reachable - Notifying listeners");
            }
            return false;
        }
    }

}
