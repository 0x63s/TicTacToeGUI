package ch.fhnw.stefan_kenan.tictactoegui.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingChecker {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final NetworkHandler networkHandler;
    private final ConnectionStatusListener listener;
    private boolean keepAlive;

    public PingChecker(NetworkHandler networkHandler, ConnectionStatusListener listener) {
        this.networkHandler = networkHandler;
        this.listener = listener;
        this.keepAlive = true;
    }

    public void startPingTask() {
        final Runnable pingTask = () -> {
            try {
                networkHandler.sendGetRequest(NetworkHandler.pingUrl);
                // Connection successful
                listener.onConnected();
            } catch (Exception e) {
                // Connection failed
                listener.onDisconnected();
                stopPingTask();
            }
        };

        scheduler.scheduleAtFixedRate(pingTask, 0, 3, TimeUnit.SECONDS);
    }

    public void stopPingTask() {
        keepAlive = false;
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
}
