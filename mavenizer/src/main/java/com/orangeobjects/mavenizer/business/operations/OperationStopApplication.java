/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;

/**
 *
 * @author michael
 */
public class OperationStopApplication extends AbstractOperation {

    @Override
    public void execute() throws OperationException {
    }

    @Override
    public String getName() {
        return "stop application";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SYSTEM_STOP_APPLICATION;
    }
}
