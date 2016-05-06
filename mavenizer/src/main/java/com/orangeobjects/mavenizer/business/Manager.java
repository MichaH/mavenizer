/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class Manager {
    
    private final static Logger LOGGER = Logger.getLogger(Manager.class.getName());

    private BlockingQueue<Operation> operationQ = new LinkedBlockingQueue<>();
    
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
                    if (nextOperation.getOperationType() == OperationType.SYSTEM_STOP_EXECUTOR) {
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
        }
    };
}
