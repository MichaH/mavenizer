/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.util;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
 */
public class SimpleObserverable extends Observable {

    static final Logger LOGGER = Logger.getLogger(SimpleObserverable.class.getName());
    
    /*  ***********************************************************************
     *  C o n s t r u c t o r
     **************************************************************************/
    

    /*  ***********************************************************************
     *  M i s c
     **************************************************************************/
    
    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    /*  ***********************************************************************
     *  G e t t e r  und  S e t t e r
     **************************************************************************/
}
