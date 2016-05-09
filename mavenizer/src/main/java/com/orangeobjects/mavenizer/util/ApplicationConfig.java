/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;

public class ApplicationConfig {

    static final Logger LOGGER = Logger.getLogger(ApplicationConfig.class.getName());
    
    public final static String PATH_TO_CONFIG = "com.orangeobjects.mavenizer.config.path";
    private Properties properties = null;
    
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read  = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();
    
    public ApplicationConfig() {
        try {
            load();
        } catch (ValidatorException | IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    public static ApplicationConfig getInstance() {
        return ApplicationConfigHolder.INSTANCE;
    }

    private static class ApplicationConfigHolder {

        private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    }
    
    private void load() throws ValidatorException, IOException {
        String path = System.getProperty(PATH_TO_CONFIG);
        if (StringUtils.isBlank(path)) {
            throw new ValidatorException("can't load application configuration "
                    + "file. please set system property '" + PATH_TO_CONFIG + "'");
        }
        LOGGER.log(Level.INFO, "found application configuration system property: {0}", path);
        File file = new File(path);
        if ( ! file.exists() || ! file.canRead()) {
            throw new IOException("configuration file " 
                    + file.getCanonicalPath() + " is unreadable");
        }
        if (write.tryLock()) {
            try {
              properties = new Properties();
              properties.load(new FileInputStream(file));
            } finally {
              write.unlock();
            }
        }
        LOGGER.info("application configuration has been loaded");
    }

    public String getProperty(String key) 
            throws InterruptedException, TimeoutException, ValidatorException {
        if (read.tryLock(10, TimeUnit.SECONDS)) {
            try {
                if (properties == null) {
                    throw new ValidatorException("application configuration has not been loaded");
                }
                return properties.getProperty(key);
            } finally {
                read.unlock();
            }
        }
        throw new TimeoutException("timeout while waiting for properties");
    }
    
    public String getProperty(String key, String defaultValue) 
            throws InterruptedException, TimeoutException, ValidatorException {
        if (read.tryLock(10, TimeUnit.SECONDS)) {
            try {
                if (properties == null) {
                    throw new ValidatorException("application configuration has not been loaded");
                }
                return properties.getProperty(key, defaultValue);
            } finally {
                read.unlock();
            }
        }
        throw new TimeoutException("timeout while waiting for properties");
    }    
}
