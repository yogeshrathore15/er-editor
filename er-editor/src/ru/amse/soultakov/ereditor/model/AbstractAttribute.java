/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public abstract class AbstractAttribute {

    protected String name;

    protected String defaultValue;

    public AbstractAttribute(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public abstract IAttributeType getType();

    public abstract boolean isNotNull();

    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public String toString() {
        return getName() + " : " + getType().getName();
    }

}