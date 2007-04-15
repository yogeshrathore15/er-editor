/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.model.SimpleAttributeType.CHAR;

/**
 * @author Soultakov Maxim
 * 
 */
public class ArrayAttributeType implements IAttributeType {

    private SimpleAttributeType arrayType;

    private int size;

    public ArrayAttributeType(SimpleAttributeType arrayType, int size) {
        if (size <= 0 || arrayType == null) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.arrayType = arrayType;
        this.size = size;
    }

    public SimpleAttributeType getArrayType() {
        return this.arrayType;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setArrayType(SimpleAttributeType arrayType) {
        this.arrayType = arrayType;
    }

    public String getName() {
        return arrayType.getName() + "[" + size + "]";
    }

    public boolean isCorrectString(String value) {
        if (arrayType == CHAR) {
            return true;// ?
        }
        return false;
    }

    public String makeCorrectLiteral(String value) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result
                + ((this.arrayType == null) ? 0 : this.arrayType.hashCode());
        result = PRIME * result + this.size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ArrayAttributeType other = (ArrayAttributeType) obj;
        if (this.arrayType == null) {
            if (other.arrayType != null)
                return false;
        } else if (!this.arrayType.equals(other.arrayType))
            return false;
        if (this.size != other.size)
            return false;
        return true;
    }

    public IAttributeType copy() {
        return new ArrayAttributeType(arrayType, size);
    }

}
