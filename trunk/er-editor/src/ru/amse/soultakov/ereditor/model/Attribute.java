package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 * 
 */
public class Attribute {
    /**
     * 
     */
    private String name;
    
    private AttributeType type;
    
    private boolean notNull;

    /**
     * @param name
     */
    public Attribute(String name, AttributeType type, boolean notNull) {
        super();
        this.name = name;
        this.type = type;
        this.notNull = notNull;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public AttributeType getType() {
        return type;
    }
    
    public void setType(AttributeType type) {
        this.type = type;
    }
    
    public boolean isNotNull() {
        return notNull;
    }
    
    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

}
