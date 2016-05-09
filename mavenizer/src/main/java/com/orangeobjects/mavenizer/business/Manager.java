/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business;

import com.orangeobjects.mavenizer.business.operations.OperationStopApplication;
import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.Library;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author michael
 */
public class Manager {
    
    private final static Logger LOGGER = Logger.getLogger(Manager.class.getName());

    private BlockingQueue<Operation> operationQ = new LinkedBlockingQueue<>();
    private ObservableSet<Library> libCollection = FXCollections
            .observableSet(new TreeSet<>());

    private Manager() {
    }
    
    public static Manager getInstance() {
        return ManagerHolder.INSTANCE;
    }
    
    private static class ManagerHolder {
        private static final Manager INSTANCE = new Manager();
    }
    
    public void start() {
        LOGGER.info("operation executor is starting...");
        Thread executor = new Thread(operationExecutor);
        executor.start();
        LOGGER.info("... started");
    }

    public boolean add(Operation op) {
        return operationQ.add(op);
    }
    
    private final Runnable operationExecutor = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Operation nextOperation = operationQ.take();
                    if (nextOperation.getOperationType() == OperationType.SYSTEM_STOP_APPLICATION) {
                        LOGGER.info("operation executor is ending...");
                        break;
                    }
                    LOGGER.log(Level.INFO, "operation executor took this: {0}", 
                            nextOperation.getName());
                    nextOperation.execute();
                    LOGGER.info("...done");
                } catch (InterruptedException | OperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            Platform.exit();
            System.exit(0);
        }
    };

    public ObservableSet<Library> getLibCollection() {
        return libCollection;
    }
    

    public void opAddLib(JarLibrary lib) {
        libCollection.add(lib);
    }
    
    public void opStopApplication() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setHeaderText("Do you really want to leave?");                      
        a.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Manager.getInstance().add(new OperationStopApplication());
            }
        });
    }
}
