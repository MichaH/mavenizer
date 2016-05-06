/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */
package com.orangeobjects.mavenizer.data;

import java.nio.file.Path;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class JarLibrary implements Library {

    private final Path originalPath;
    // only a cache for the immutable extracted name
    private final String originalName;
    // only a cache for the immutable extracted version
    private final Optional<String> optOriginalVersion;

    private boolean inheritedGroupId = true;
    private final StringProperty groupId = new SimpleStringProperty();
    private final StringProperty artifactId = new SimpleStringProperty();
    private boolean inheritedVersion = true;
    private final StringProperty version = new SimpleStringProperty();
    private String scope = "compile";
    private String type = "jar";
    
    private boolean pomDependency = true;
    private boolean install = true;
    
    public JarLibrary(Path path) {
        originalPath = path;
        originalName = "originalName";
        optOriginalVersion = Optional.of("1.0");
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPomDependency() {
        return pomDependency;
    }

    public void setPomDependency(boolean pomDependency) {
        this.pomDependency = pomDependency;
    }

    public boolean isInstall() {
        return install;
    }

    public void setInstall(boolean install) {
        this.install = install;
    }

    public boolean isInheritedGroupId() {
        return inheritedGroupId;
    }

    public void setInheritedGroupId(boolean inheritedGroupId) {
        this.inheritedGroupId = inheritedGroupId;
    }

    public boolean isInheritedVersion() {
        return inheritedVersion;
    }

    public void setInheritedVersion(boolean inheritedVersion) {
        this.inheritedVersion = inheritedVersion;
    }

    public String getVersion() {
        return version.get();
    }

    public void setVersion(String value) {
        version.set(value);
    }

    public StringProperty versionProperty() {
        return version;
    }

    public String getGroupId() {
        return groupId.get();
    }

    public void setGroupId(String value) {
        groupId.set(value);
    }

    public StringProperty groupIdProperty() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId.get();
    }

    public void setArtifactId(String value) {
        artifactId.set(value);
    }

    public StringProperty artifactIdProperty() {
        return artifactId;
    }
    
    public String getOriginalName() {
        return originalName;
    }

    public Path getOriginalPath() {
        return originalPath;
    }

    public Optional<String> getOptOriginalVersion() {
        return optOriginalVersion;
    }
}
