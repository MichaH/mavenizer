/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;
import com.orangeobjects.mavenizer.data.JarLibrary;
import java.io.File;

/**
 *
 * @author michael
 */
public class OperationAddLib extends AbstractOperation {

    private final File originalFile;

    public OperationAddLib(File originalFile) {
        this.originalFile = originalFile;
    }
    
    @Override
    public void execute() throws OperationException {
        JarLibrary lib = new JarLibrary(originalFile);
        Manager.getInstance().opAddLib(lib);
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
