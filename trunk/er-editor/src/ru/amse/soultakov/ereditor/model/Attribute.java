package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 * 
 */
public class Attribute extends AbstractAttribute {

    private IAttributeType type;

    private boolean notNull;

    /**
     * @param name
     */
    public Attribute(String name, IAttributeType type, boolean notNull,
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

    public void setType(IAttributeType type) {
        this.type = type;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public IAttributeType getType() {
        return type;
    }

    @Override
    public boolean isNotNull() {
        return notNull;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
