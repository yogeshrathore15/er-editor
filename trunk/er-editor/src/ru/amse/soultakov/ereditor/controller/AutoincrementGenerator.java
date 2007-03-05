/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.controller;

public class AutoincrementGenerator {

    private static int currentNumber;
    
    private AutoincrementGenerator() {
        
    }
    
    public static synchronized int getNextInteger() {
        return currentNumber++;
    }
}
