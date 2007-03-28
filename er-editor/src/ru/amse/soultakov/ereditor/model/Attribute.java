package ru.amse.soultakov.ereditor.model;


/**
 * @author sma
 * 
 */
public class Attribute extends AbstractAttribute {
    
    private SimpleAttributeType type;

    private boolean notNull;

    /**
     * @param name
     */
    public Attribute(String name, SimpleAttributeType type, boolean notNull,
            String defaultValue) {
        super(name, defaultValue);
        this.type = type;
        this.notNull = notNull;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setType(SimpleAttributeType type) {
        this.type = type;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public SimpleAttributeType getType() {
        return type;
    }

    @Override
    public boolean isNotNull() {
        return notNull;
    }

}
