/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */
package com.orangeobjects.mavenizer.data;

import java.io.File;
import java.util.logging.Logger;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class LibraryBean {

    private final static Logger LOGGER = Logger.getLogger(LibraryBean.class.getName());
    
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
    
    // system fields
    private String lastCreator;
    private long lastDataVersionNo;
    
    public LibraryBean(File file) {
        assert file != null;
        originalFile = file;
        lastDataVersionNo = 1L;
    }
    
    public static LibraryBean build(File file) {
        return new LibraryBean(file);
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

    public LibraryBean setInheritedGroupId(boolean inheritedGroupId) {
        this.inheritedGroupId = inheritedGroupId;
        return this;
    }

    public boolean isInheritedVersion() {
        return inheritedVersion;
    }

    public LibraryBean setInheritedVersion(boolean inheritedVersion) {
        this.inheritedVersion = inheritedVersion;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public LibraryBean setGroupId(String groupId) {
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
    
    public String getNewLibraryName() {
        return getArtifactId() + "-" + getVersion();
    }
}
