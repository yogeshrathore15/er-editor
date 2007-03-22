/*
 * Created on 17.02.2007
 */
package ru.amse.soultakov.multithreading.tasksandworkers;

import java.util.concurrent.BlockingQueue;

/**
 * This class provides publishing results of some computations. It takes values
 * from some <class>BlockingQueue</class>
 * 
 * @param T
 *            the type of values to publish.
 * 
 * @author Soultakov Maxim
 */
public class Publisher<T> implements Runnable {

    /**
     * The values to publish are taken from this queue
     */
    private BlockingQueue<T> valuesToPublish;

    /**
     * Creates the instance of <code>Publisher</code> with specified blocking
     * queue
     * 
     * @param valuesToPublish
     *            blocking queue from which the values to publish will be taken
     */
    public Publisher(BlockingQueue<T> valuesToPublish) {
        this.valuesToPublish = valuesToPublish;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            while (true) {
                System.out.println(valuesToPublish.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
