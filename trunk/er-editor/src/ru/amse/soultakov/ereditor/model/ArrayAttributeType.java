/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public class ArrayAttributeType implements IAttributeType {

    private SimpleAttributeType arrayType;
    private int dimension;
    
    public ArrayAttributeType(SimpleAttributeType arrayType, int dimension) {
        this.arrayType = arrayType;
        this.dimension = dimension;
    }
    
    public SimpleAttributeType getArrayType() {
        return this.arrayType;
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    
    public void setArrayType(SimpleAttributeType arrayType) {
        this.arrayType = arrayType;
    }

    public String getName() {
        return arrayType.getName() + "[" + dimension + "]";
    }

    public boolean isCorrectString(String value) {
        return false;
    }

    public String makeCorrectLiteral(String value) {
        throw new UnsupportedOperationException("Not supported yet");
    }

}
