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
public class OperationRemoveAll extends AbstractOperation {

    @Override
    public void execute() throws OperationException {
        Manager.getInstance().opRemoveAllLibraries();
    }

    @Override
    public String getName() {
        return "Remove all Libraries from collection";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.BUSINESS;
    }
}
