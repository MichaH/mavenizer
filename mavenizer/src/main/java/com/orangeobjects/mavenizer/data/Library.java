/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.data;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author michael
 */
public interface Library {
    
    public static final ObservableList<String> SCOPE_LIST
            = FXCollections.observableArrayList("", "compile", "provided", 
                    "runtime", "test", "system", "import");
    public static final ObservableList<String> TYPE_LIST 
            = FXCollections.observableArrayList("", "jar");
    
    int getId();
    public File getOriginalFile();
    public String getGroupId();
    public String getArtifactId();
    public String getVersion();
    String getDisplayName();
}
