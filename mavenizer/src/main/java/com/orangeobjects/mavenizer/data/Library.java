/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.data;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
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
    public String getEffectiveGroupId();
    public String getArtifactId();
    public String getVersion();
    public String getEffectiveVersion();
    public String getScope();
    public String getType();
    String getDisplayName();
    boolean isInstall();
    boolean isPomDependency();
    String getNewLibraryName();
    
    String getLastCreator();
    long getLastDataVersionNo();
}
