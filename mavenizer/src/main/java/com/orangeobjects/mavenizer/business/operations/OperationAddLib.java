/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;
import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.LibrariesCollection;
import java.nio.file.Path;

/**
 *
 * @author michael
 */
public class OperationAddLib extends AbstractOperation {

    private final Path originalPath;

    public OperationAddLib(Path originalPath) {
        this.originalPath = originalPath;
    }
    
    @Override
    public void execute() throws OperationException {
        JarLibrary lib = new JarLibrary(originalPath);
        LibrariesCollection.getInstance().add(lib);
    }

    @Override
    public String getName() {
        return "Add a new .jar-Library to collection";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.BUSINESS;
    }
}
