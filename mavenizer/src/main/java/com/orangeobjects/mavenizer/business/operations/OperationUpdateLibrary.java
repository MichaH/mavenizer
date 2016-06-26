/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;
import com.orangeobjects.mavenizer.data.LibraryBean;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
 */
public class OperationUpdateLibrary extends AbstractOperation {
    
    private final LibraryBean libraryBean;

    public OperationUpdateLibrary(LibraryBean libraryBean) {
        this.libraryBean = libraryBean;
    }
    
    @Override
    public void execute() throws OperationException {
        Manager.getInstance().opUpdateLib(libraryBean);
    }

    @Override
    public String getName() {
        return "Updates values of a .jar-Library";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.BUSINESS;
    }
}
