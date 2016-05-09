/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.data;

import java.io.File;

/**
 *
 * @author michael
 */
public interface Library {
    
    int getId();
    public File getOriginalFile();
    public String getGroupId();
    public String getArtifactId();
    public String getVersion();
    String getDisplayName();
}
