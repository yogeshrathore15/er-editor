/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.controller;

class AutoincrementGenerator {

    private int currentNumber;

    public AutoincrementGenerator() {

    }

    public synchronized int getNextInteger() {
        return currentNumber++;
    }
}
