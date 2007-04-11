/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.util;

public class AutoincrementGenerator {

    private long currentNumber;

    private final Object lock = new Object();

    public AutoincrementGenerator(int startFrom) {
        currentNumber = startFrom;
    }

    public AutoincrementGenerator() {
        this(0);
    }

    public long getNextNumber() {
        synchronized (lock) {
            return currentNumber++;
        }
    }
}
