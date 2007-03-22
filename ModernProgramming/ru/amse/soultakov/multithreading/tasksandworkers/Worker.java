/*
 * Created on 17.02.2007
 */
package ru.amse.soultakov.multithreading.tasksandworkers;

import java.util.concurrent.BlockingQueue;

/**
 * This class provides solving some problem: it takes value from one blocking
 * queue, then calculates sum from 1 to taken value and puts the result to
 * another blocking queue
 * 
 * @author Soultakov Maxim
 */
public class Worker implements Runnable {

    /**
     * The blocking queue, which contains tasks(integer numbers)
     */
    private BlockingQueue<Integer> tasksQueue;

    /**
     * The blocking queue, which contains results(integer numbers)
     */
    private BlockingQueue<Integer> resultsQueue;

    /**
     * Creates the new instance of <code>Worker</code> with specified tasks
     * and results queues.
     * 
     * @param tasksQueue
     *            the queue, where values are taken from
     * @param resultsQueue
     *            the queue, where values are putting to
     */
    public Worker(BlockingQueue<Integer> tasksQueue,
            BlockingQueue<Integer> resultsQueue) {
        if ((tasksQueue == null) || (resultsQueue == null)) {
            throw new IllegalArgumentException(
                    "Both queues must be non-null references");
        }
        this.tasksQueue = tasksQueue;
        this.resultsQueue = resultsQueue;
    }

    /**
     * Starts the process of taking values, calculating and putting results to
     * queue.
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            while (true) {
                synchronized (Worker.class) {
                    int number = tasksQueue.take();
                    int sum = 0;
                    for (int i = 1; i <= number; i++) {
                        sum += i;
                    }
                    resultsQueue.put(sum);
                }
            }
        } catch (InterruptedException e) {
        }
    }

}
