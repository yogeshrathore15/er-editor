/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public class ArrayAttributeType implements IAttributeType {

    private SimpleAttributeType arrayType;
    
    public ArrayAttributeType(SimpleAttributeType arrayType) {
        this.arrayType = arrayType;
    }
    
    public SimpleAttributeType getArrayType() {
        return this.arrayType;
    }
    
    public void setArrayType(SimpleAttributeType arrayType) {
        this.arrayType = arrayType;
    }

    public String getName() {
        return arrayType.getName() + "[]";
    }

    public boolean isCorrectString(String value) {
        return false;
    }

    public String makeCorrectLiteral(String value) {
        throw new UnsupportedOperationException("Not supported yet");
    }

}
