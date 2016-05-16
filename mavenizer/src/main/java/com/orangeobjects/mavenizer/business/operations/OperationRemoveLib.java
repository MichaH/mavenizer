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

/**
 *
 * @author michael
 */
public class OperationRemoveLib extends AbstractOperation {

    private final int id;

    public OperationRemoveLib(int id) {
        this.id = id;
    }
    
    @Override
    public void execute() throws OperationException {
        Manager.getInstance().getLibrarySet().forEach(lib -> {
            if (lib.getId() == this.id) {
                Manager.getInstance().opRemoveLib(lib);
            }
        });
    }

    @Override
    public String getName() {
        return "Remove a .jar-Library from collection";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.BUSINESS;
    }
}
