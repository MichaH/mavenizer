/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.data;

import java.util.TreeSet;

/**
 *
 * @author michael
 */
public class LibrariesCollection extends TreeSet<Library> {

    private static final long serialVersionUID = -1958638476332610233L;
    
    private LibrariesCollection() {
    }
    
    public static LibrariesCollection getInstance() {
        return LibrariesCollectionHolder.INSTANCE;
    }
    
    private static class LibrariesCollectionHolder {

        private static final LibrariesCollection INSTANCE = new LibrariesCollection();
    }
}
