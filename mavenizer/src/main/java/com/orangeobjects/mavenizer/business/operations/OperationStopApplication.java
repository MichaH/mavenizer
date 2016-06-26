/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
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
