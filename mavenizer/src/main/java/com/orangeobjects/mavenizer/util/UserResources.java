/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author michael
 */
public class UserResources extends Properties {

    private final static Logger LOGGER = Logger.getLogger(UserResources.class.getName());
    private static final long serialVersionUID = 4676930718587001989L;
    
    private static final String RC_FILEPATH = ".orangeobjects/mavenizer/mavenizer.properties";

    public void load() {
        try {
            File rcFile = getResourceFile().orElse(null);
            if ((rcFile != null) && rcFile.exists() && rcFile.canRead()) {
                LOGGER.log(Level.INFO, "resource file exists; {0}", rcFile.getAbsolutePath());
                InputStream is = new FileInputStream(rcFile);
                load(is);
                LOGGER.info("resource file has been loaded");
            } else {
                LOGGER.warning("can't find or load resource file");
            }
        } catch (IOException iOException) {
            LOGGER.log(Level.SEVERE, "error while loading resource file", iOException);
        }
    }

    public void save() {
        try {
            File rcFile = getResourceFile().orElse(null);
            if (rcFile != null) {
                if ( ! rcFile.exists()) {
                    rcFile.getParentFile().mkdirs();
                    rcFile.createNewFile();
                }                
                OutputStream os = new FileOutputStream(rcFile);
                store(os, new Date().toString());
                LOGGER.log(Level.INFO, "resource file has been written; {0}", 
                        rcFile.getAbsolutePath());
            } else {
                LOGGER.warning("can't write resource file");
            }
        } catch (IOException iOException) {
            LOGGER.log(Level.SEVERE, "error while loading resource file", iOException);
        }
    }
    
    private Optional<File> getResourceFile() {
        String userHomeStr = System.getProperty("user.home");
        if (StringUtils.isBlank(userHomeStr)) {
            return Optional.empty();
        }
        return Optional.of(new File(userHomeStr, RC_FILEPATH));
    }
}
