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
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class JarLibrary extends LibraryBean implements Library, Comparable<Library> {

    private final static Logger LOGGER = Logger.getLogger(JarLibrary.class.getName());
    // ^([-\w]*[a-zA-Z]+)[-_]*([0-9\.]*).([a-zA-Z]{3})$
    private final static String FILENAME_PATTERN 
            = "^([-\\w]*[a-zA-Z]+)[-_]*([0-9\\.]*).([a-zA-Z]{3})$";
    private final Pattern pattern = Pattern.compile(FILENAME_PATTERN);
    
    private static int nextId = 0;
    
    private final int id;                                // example: 1
    // only a cache for the immutable extracted name
    private final String originalName;                   // example: wordTrans-2.3.jar
    // only a cache for the immutable extracted artefact name
    private final String originalArtefactname;           // example: wordTrans
    // only a cache for the immutable extracted version
    private final Optional<String> optOriginalVersion;   // example: 2.3
    
    public JarLibrary(File file) {
        super(file);
        id = ++nextId;
        
        // name examination
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
        
        setArtifactId(getOriginalArtefactname());
        
        setInheritedVersion( !optOriginalVersion.isPresent() && propStartInheritedVersion);
        setVersion(optOriginalVersion.orElse(null));
        
        setInheritedGroupId(propStartInheritedGroupId);
    }
    
    public String getOriginalName() {
        return originalName;
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
        hash = 59 * hash + Objects.hashCode(getOriginalFile().getAbsolutePath());
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

    final public String getOriginalArtefactname() {
        return originalArtefactname;
    }

    @Override
    public String getGroupId() {
        return super.getGroupId();
    }
    
    @Override
    public String getVersion() {
        return super.getVersion();
    }
    
    @Override
    public String getEffectiveGroupId() {
        return isInheritedGroupId() ? propInheritedGroupId : getGroupId();
    }

    @Override
    public String getEffectiveVersion() {
        return isInheritedVersion() ? propInheritedVersion : getVersion();
    }
    
    @Override
    public String getNewLibraryName() {
        return getArtifactId() + "-" + getEffectiveVersion();
    }
}
