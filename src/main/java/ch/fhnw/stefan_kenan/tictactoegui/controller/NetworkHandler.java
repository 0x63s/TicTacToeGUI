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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import static java.lang.Thread.*;

public class NetworkHandler implements ConnectionStatusInterface {
    private static NetworkHandler instance;
    private final Logger logger = LogManager.getLogger(NetworkHandler.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean connected = false;
    private boolean keepAlive = true;
    private static String endpointUrl = "http://localhost/";
    private static String port = "50005";
    public static final String pingUrl = "ping";
    public static final String registerUrl = "users/register";
    public static final String loginUrl = "users/login";
    public static final String logoutUrl = "users/logout";
    public static final String createGameUrl = "game/new";
    private ConnectionStatusListener listener;

    NetworkHandler(){
        listener = new ConnectionStatusListener();
        listener.addListener(this);
        keepAlive = false;
        endpointUrl = "";
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


    public void stopListening() {
        keepAlive = false;
    }

    public JSONObject sendGetRequest(String urlStr) throws Exception {
        final String uri = endpointUrl + ":" + port + "/" + urlStr;
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

    public JSONObject sendPostRequest(String urlStr, String body) throws Exception {
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
        } catch(Exception e) {
            logger.error("Error while processing Post Request!", e);
        } finally {
            logger.debug("Closing connection to: " + urlStr);
            con.disconnect();
        }
        return null;
    }


    /*

        The following methods are used to check if the server is reachable.

     */
    public void startPingTask() {
        /*
        if (!scheduler.isShutdown()) {
            logger.debug("Ping task is already running");
            return;
        }

         */

        final Runnable pingTask = () -> {
            try {
                sendGetRequest(pingUrl);
                connected = true; // Connection successful

                //TODO: Enable login/logout/register buttons
                //TODO: Disable ping button
                logger.debug("Triggering onConnected event");
                listener.onConnected();



            } catch (Exception e) {
                connected = false; // Connection failed
                stopPingTask();
                logger.error("Ping failed. Stopping ping task.", e);
                User.getInstance().clearUser();

                //TODO: Disable login/logout/register buttons
                //TODO: Enable ping button
                logger.debug("Triggering onDisconnected event");
                listener.onDisconnected();
                stopPingTask();
            }
        };

        scheduler.scheduleAtFixedRate(pingTask, 0, 3, TimeUnit.SECONDS);
    }



    public void stopPingTask() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }
}
