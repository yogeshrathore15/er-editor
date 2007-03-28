/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public interface IAttributeType {

    String getName();
    
    boolean isCorrectString(String value);
    
    String makeCorrectLiteral(String value);
    
}
