/*
 * Created on 13.02.2007
 */
package ru.amse.soultakov.multithreading.tasksandworkers;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * This class provides generating pseudo-random posivive integers and putting
 * them to <code>BlockingQueue</code>
 * 
 * @author Soultakov Maxim
 */
public class TasksGenerator implements Runnable {

    /**
     * The maximum int value which may be generated
     */
    private static final int MAX_VALUE = 20;

    /**
     * Generated values are put to this blocking queue
     */
    private BlockingQueue<Integer> tasksQueue;

    /**
     * Provides generation pseudo-random numbers
     */
    private Random random = new Random();

    /**
     * Creates new instance of <code>TasksGenerator</code>
     * 
     * @param tasksQueue
     *            the <code>BlockingQueue</code> where generated values are
     *            put
     */
    public TasksGenerator(BlockingQueue<Integer> tasksQueue) {
        this.tasksQueue = tasksQueue;
    }

    /** 
     * Starts the process of generating 
     */
    public void run() {
        try {
            while (true) {
                int nextInt = random.nextInt(TasksGenerator.MAX_VALUE + 1);
                tasksQueue.put(nextInt);
                System.out.println("Generated " + nextInt);
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
        }
    }

}
