/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public class FKAttribute extends AbstractAttribute {

    private Entity entity;

    private Attribute attribute;

    public FKAttribute(String name, boolean notNull, String defaultValue,
            Entity entity, Attribute attribute) {
        super(name, defaultValue);
        this.entity = entity;
        this.attribute = attribute;
    }

    @Override
    public IAttributeType getType() {
        return attribute.getType();
    }

    public Entity getEntity() {
        return this.entity;
    }
    
    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean isNotNull() {
        return true;
    }

}
