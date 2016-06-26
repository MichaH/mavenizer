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
public class OperationException extends Exception {

    /**
     * Creates a new instance of <code>OperationException</code> without detail message.
     */
    public OperationException() {
    }

    /**
     * Constructs an instance of <code>OperationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public OperationException(String msg) {
        super(msg);
    }
}
