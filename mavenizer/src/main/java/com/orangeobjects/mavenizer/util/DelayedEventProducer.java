/*
 *  O R A N G E   O B J E C T S
 *  copyright by Orange Objects
 * 
 *  http://www.OrangeObjects.de
 * 
 *  $Id$
 */
package com.orangeobjects.mavenizer.util;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 */
public class DelayedEventProducer extends Observable {

    static final Logger LOGGER = Logger.getLogger(DelayedEventProducer.class.getName());

    // if there is a lock, a timer is running
    private final AtomicBoolean timerActiv = new AtomicBoolean(false);

    private final static long PER_ROUND = 500;   // ms
    private final AtomicLong countdown = new AtomicLong();
    private final long maxDelay;

    /*  ***********************************************************************
     *  C o n s t r u c t o r
     **************************************************************************/
    
    public DelayedEventProducer(long maxDelay) {
        this.maxDelay = maxDelay;
    }

    /*  ***********************************************************************
     *  M i s c
     **************************************************************************/
    
    @Override
    public void notifyObservers() {
        countdown.set(maxDelay);
        setChanged();
        if (timerActiv.weakCompareAndSet(false, true)) {
            new Thread(() -> {
                try {
                    while (countdown.getAndAdd(PER_ROUND * (-1)) >= 0) {
                        Thread.sleep(PER_ROUND);
                    }
                    DelayedEventProducer.super.notifyObservers();
                } catch (InterruptedException ex) {
                    LOGGER.log(Level.SEVERE, "error", ex);
                } finally {
                    timerActiv.set(false);
                }
            }).start();
        }
    }

    /*  ***********************************************************************
     *  G e t t e r  und  S e t t e r
     **************************************************************************/
}
