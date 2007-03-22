/*
 * Created on 13.02.2007
 */
package ru.amse.soultakov.multithreading.tasksandworkers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final int THREAD_COUNT = 10;
    private static final int MAX_QUEUE_SIZE = 100;
    
    public static void main(String[] args) {
        BlockingQueue<Integer> tasksQueue = new LinkedBlockingQueue<Integer>(MAX_QUEUE_SIZE);
        BlockingQueue<Integer> resultsQueue = new LinkedBlockingQueue<Integer>(MAX_QUEUE_SIZE);
        for(int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new TasksGenerator(tasksQueue)).start();
            new Thread(new Worker(tasksQueue, resultsQueue)).start();
            new Thread(new Publisher<Integer>(resultsQueue)).start();
        }
    }

}
