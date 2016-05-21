/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business;

import com.orangeobjects.mavenizer.business.operations.OperationStopApplication;
import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.Library;
import com.orangeobjects.mavenizer.util.ApplicationConfig;
import com.orangeobjects.mavenizer.util.DelayedEventProducer;
import com.orangeobjects.mavenizer.util.UserResources;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author michael
 */
public class Manager {
    
    private final static Logger LOGGER = Logger.getLogger(Manager.class.getName());
    
    private ApplicationConfig config;
    private final UserResources userResources;

    private BlockingQueue<Operation> operationQ = new LinkedBlockingQueue<>();
    private final ObservableSet<Library> libCollection = FXCollections
            .observableSet(new TreeSet<>());
    private DelayedEventProducer signalizer;

    private Manager() {
        // loading resource file from user home if possible
        userResources = new UserResources();
        userResources.load();
    }
    
    public static Manager getInstance() {
        return ManagerHolder.INSTANCE;
    }

    public DelayedEventProducer getSignalizer() {
        return signalizer;
    }
    
    private static class ManagerHolder {
        private static final Manager INSTANCE = new Manager();
    }
    
    public void start() {
        
        this.signalizer = new DelayedEventProducer(1500L);
        libCollection.addListener(new MyChangeListener());
        
        config = ApplicationConfig.getInstance();
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
            userResources.save();
            Platform.exit();
            System.exit(0);
        }
    };

    public ObservableSet<Library> getLibCollection() {
        return libCollection;
    }
    
    public Set<Library> getLibrarySet() {
        return getLibCollection().stream().collect(Collectors.toSet());
    }
    

    public void opAddLib(JarLibrary lib) {
        boolean added = libCollection.add(lib);
        if ( ! added) {
            LOGGER.log(Level.INFO, "jar-Lib ''{0}'' has already been in collection",
                    lib.getDisplayName());
        }
    }

    public void opRemoveLib(Library lib) {
        boolean removed = libCollection.remove(lib);
        if ( ! removed) {
            LOGGER.log(Level.INFO, "jar-Lib ''{0}'' couldn't been removed from collection",
                    lib.getDisplayName());
        }
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
    
    
    private class MyChangeListener implements SetChangeListener<Library> {
        @Override
        public void onChanged(SetChangeListener.Change<? extends Library> change) {
            signalizer.notifyObservers();
        }
    }
    
    public UserResources getUserResources() {
        return userResources;
    }
}
