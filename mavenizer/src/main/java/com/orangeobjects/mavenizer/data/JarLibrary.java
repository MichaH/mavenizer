/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */
package com.orangeobjects.mavenizer.data;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class JarLibrary implements Library, Comparable<Library> {

    private final static Logger LOGGER = Logger.getLogger(JarLibrary.class.getName());
    // ^([-\w]*[a-zA-Z]+)[-_]*([0-9\.]*).([a-zA-Z]{3})$
    private final static String FILENAME_PATTERN = "^([-\\w]*[a-zA-Z]+)[-_]*([0-9\\.]*).([a-zA-Z]{3})$";
    private final Pattern pattern = Pattern.compile(FILENAME_PATTERN);
    
    private static int nextId = 0;
    
    private final int id;                                // example: 1
    private final File originalFile;                     // example: /src/target/wordTrans-2.3.jar
    // only a cache for the immutable extracted name
    private final String originalName;                   // example: wordTrans-2.3.jar
    // only a cache for the immutable extracted artefact name
    private final String originalArtefactname;           // example: wordTrans
    // only a cache for the immutable extracted version
    private final Optional<String> optOriginalVersion;   // example: 2.3

    private boolean inheritedGroupId = true;
    private final StringProperty groupId = new SimpleStringProperty();
    private final StringProperty artifactId = new SimpleStringProperty();
    private boolean inheritedVersion = true;
    private final StringProperty version = new SimpleStringProperty();
    private String scope = "compile";
    private String type = "jar";
    
    private boolean pomDependency = true;
    private boolean install = true;
    
    public JarLibrary(File file) {
        assert file != null;
        
        id = ++nextId;
        // name examination
        originalFile = file;
        originalName = file.getName();
        Matcher m = null;
        try {
            m = pattern.matcher(originalName);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        originalArtefactname = m != null && m.matches() && StringUtils.isNotBlank(m.group(1)) ? 
                m.group(1) : originalName;
        optOriginalVersion = m != null && m.matches() && StringUtils.isNotBlank(m.group(2)) ? 
                Optional.of(m.group(2)) : Optional.empty();
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

    @Override
    public String getVersion() {
        return version.get();
    }

    public void setVersion(String value) {
        version.set(value);
    }

    public StringProperty versionProperty() {
        return version;
    }

    @Override
    public String getGroupId() {
        return groupId.get();
    }

    public void setGroupId(String value) {
        groupId.set(value);
    }

    public StringProperty groupIdProperty() {
        return groupId;
    }

    @Override
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

    @Override
    public File getOriginalFile() {
        return originalFile;
    }

    public Optional<String> getOptOriginalVersion() {
        return optOriginalVersion;
    }

    @Override
    public int compareTo(Library o) {
        return this.getDisplayName().compareTo(o.getDisplayName());
    }

    @Override
    public boolean equals(Object obj) {
        assert obj != null;
        if ( ! (obj instanceof Library)) {
            return false;
        }
        Library other = (Library)obj;
        return this.getOriginalFile().getAbsolutePath().equals(
                other.getOriginalFile().getAbsolutePath());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.originalFile.getAbsolutePath());
        return hash;
    }
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return getOriginalName();
    }

    public String getOriginalArtefactname() {
        return originalArtefactname;
    }
}
