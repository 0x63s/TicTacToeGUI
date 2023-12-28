package ch.fhnw.stefan_kenan.tictactoegui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static java.lang.Thread.*;

public class NetworkHandler {
    private static NetworkHandler instance;
    private final Logger logger = LogManager.getLogger(NetworkHandler.class);
    private boolean keepAlive = true;
    private static String endpointUrl = "http://localhost/";
    private static String port = "8080";
    public static final String loginUrl = "login";
    public static final String pingUrl = "ping";

    NetworkHandler(){
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

    //This
    public void startListening() {
        new Thread(() -> {
            while (keepAlive) {
                try {
                    String response = String.valueOf(sendGetRequest(endpointUrl));
                    // Process the response
                    // E.g., check if the opponent has made a move

                    // Wait for a short interval before the next request
                    sleep(1000);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                } catch (Exception e) {
                    logger.error("Error while handling Game Update!", e);

                }
            }
        }).start();
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
        logger.debug("Sending POST request to: " + urlStr);
        URL url = URI.create(urlStr).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

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
}
