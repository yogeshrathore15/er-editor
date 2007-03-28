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

    public abstract SimpleAttributeType getType();

    public abstract boolean isNotNull();

    public String getDefaultValue() {
        return this.defaultValue;
    }

}