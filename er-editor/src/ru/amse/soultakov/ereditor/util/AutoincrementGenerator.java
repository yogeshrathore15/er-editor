/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.util;

public class AutoincrementGenerator {

    private int currentNumber;

    private final Object lock = new Object();

    public AutoincrementGenerator(int startFrom) {
        currentNumber = startFrom;
    }

    public AutoincrementGenerator() {
        this(0);
    }

    public int getNextInteger() {
        synchronized (lock) {
            return currentNumber++;
        }
    }
}
