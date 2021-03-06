/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.business;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
 */
public interface Operation {
    
    String getName();
    OperationType getOperationType(); 
    void execute() throws OperationException;
    
    
}
