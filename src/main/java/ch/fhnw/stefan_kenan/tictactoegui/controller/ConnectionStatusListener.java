package ch.fhnw.stefan_kenan.tictactoegui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConnectionStatusListener {
    private List<ConnectionStatusInterface>listeners = new ArrayList();
    private Logger logger = LogManager.getLogger(ConnectionStatusListener.class);


    public void addListener(ConnectionStatusInterface toAdd) {
        logger.debug("Adding listener");
        listeners.add(toAdd);
    }

    public void removeListener(ConnectionStatusInterface toRemove){
        logger.debug("Removing listener");
        listeners.remove(toRemove);
    }

    public void onConnected(){
        logger.debug("Notifying listeners");
        for (ConnectionStatusInterface hl : listeners)
            hl.onConnected();
    }

    public void onDisconnected(){
        for (ConnectionStatusInterface hl : listeners)
            hl.onDisconnected();
    }

}
