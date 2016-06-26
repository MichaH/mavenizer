/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.data;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */

public class JarLibraryTest {
    
    public JarLibraryTest() {
    }
    
    @Test
    public void testConstructor01() {
        System.out.println("testConstructor01");
        
        File file = new File("/src/target/wordTrans-2.3.jar");
        JarLibrary lib = new JarLibrary(file);
        assertEquals("wordTrans-2.3.jar", lib.getOriginalName());
        assertEquals("wordTrans", lib.getOriginalArtefactname());
        assertEquals("2.3", lib.getOptOriginalVersion().get());
    }
    
    @Test
    public void testConstructor02() {
        System.out.println("testConstructor02");
        
        File file = new File("/src/target/wordTrans2.3.jar");
        JarLibrary lib = new JarLibrary(file);
        assertEquals("wordTrans2.3.jar", lib.getOriginalName());
        assertEquals("wordTrans", lib.getOriginalArtefactname());
        assertEquals("2.3", lib.getOptOriginalVersion().get());
    }
    
    @Test
    public void testConstructor03() {
        System.out.println("testConstructor03");
        
        File file = new File("/src/target/wordTrans.jar");
        JarLibrary lib = new JarLibrary(file);
        assertEquals("wordTrans.jar", lib.getOriginalName());
        assertEquals("wordTrans", lib.getOriginalArtefactname());
        assertFalse(lib.getOptOriginalVersion().isPresent());
    }
}
