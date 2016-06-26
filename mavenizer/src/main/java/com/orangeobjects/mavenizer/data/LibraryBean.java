/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.data;

import com.orangeobjects.mavenizer.util.ApplicationConfig;
import java.io.File;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.validator.ValidatorException;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
 */
public abstract class LibraryBean {

    private final static Logger LOGGER = Logger.getLogger(LibraryBean.class.getName());
    private final static ApplicationConfig CONFIG = ApplicationConfig.getInstance();
    
    private final File originalFile;                  // example: /src/target/wordTrans-2.3.jar
    
    private boolean inheritedGroupId;
    private String groupId;
    private String artifactId;
    private boolean inheritedVersion;
    private String version;
    private String scope;
    private String type;
    private boolean pomDependency = true;
    private boolean install = true;
    
    protected String propInheritedGroupId = null;
    protected boolean propStartInheritedGroupId = false;
    protected String propInheritedVersion = null;
    protected boolean propStartInheritedVersion = false;
    
    // system fields
    private String lastCreator;
    private long lastDataVersionNo;
    
    public LibraryBean(File file) {
        assert file != null;
        originalFile = file;
        lastDataVersionNo = 1L;
        
        try {
            propInheritedGroupId = CONFIG.getProperty(
                    "mavenizer.inherited.groupId", "com.yourCompany.name");
            propStartInheritedGroupId = Boolean.parseBoolean(CONFIG.getProperty(
                    "mavenizer.inherited.groupId.start", "false"));
            propInheritedVersion = CONFIG.getProperty(
                    "mavenizer.inherited.version", "0.0");
            propStartInheritedGroupId = Boolean.parseBoolean(CONFIG.getProperty(
                    "mavenizer.inherited.version.start", "false"));
        } catch (InterruptedException | TimeoutException | ValidatorException ex) {
            LOGGER.log(Level.SEVERE, type, ex);
        }
    }
    
    public static LibraryBean build(File file) {
        return new JarLibrary(file);
    }

    public File getOriginalFile() {
        return originalFile;
    }
    
    public String getScope() {
        return scope;
    }

    public LibraryBean setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getType() {
        return type;
    }

    public LibraryBean setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isPomDependency() {
        return pomDependency;
    }

    public LibraryBean setPomDependency(boolean pomDependency) {
        this.pomDependency = pomDependency;
        return this;
    }

    public boolean isInstall() {
        return install;
    }

    public LibraryBean setInstall(boolean install) {
        this.install = install;
        return this;
    }

    public boolean isInheritedGroupId() {
        return inheritedGroupId;
    }

    final public LibraryBean setInheritedGroupId(boolean inheritedGroupId) {
        this.inheritedGroupId = inheritedGroupId;
        return this;
    }

    public boolean isInheritedVersion() {
        return inheritedVersion;
    }

    final public LibraryBean setInheritedVersion(boolean inheritedVersion) {
        this.inheritedVersion = inheritedVersion;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    final public LibraryBean setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    final public LibraryBean setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    final public LibraryBean setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("GroupId", getGroupId())
                .append("InheritedGroupId", isInheritedGroupId())
                .append("ArtifactId", getArtifactId())
                .append("Version", getVersion())
                .append("InheritedVersion", isInheritedVersion())
                .append("isPomDependency", isPomDependency())
                .append("isInstall", isInstall())
                .append("Scope", getScope())
                .append("Type", getType())
                .toString();
    }

    public String getLastCreator() {
        return lastCreator;
    }

    public LibraryBean setLastCreator(String lastCreator) {
        this.lastCreator = lastCreator;
        return this;
    }

    public long getLastDataVersionNo() {
        return lastDataVersionNo;
    }

    public LibraryBean setLastDataVersionNo(long lastDataVersionNo) {
        this.lastDataVersionNo = lastDataVersionNo;
        return this;
    }
    
    public abstract String getNewLibraryName();
}
