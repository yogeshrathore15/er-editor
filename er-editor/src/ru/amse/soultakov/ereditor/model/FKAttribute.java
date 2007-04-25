/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author Soultakov Maxim
 *
 */
public class FKAttribute extends AbstractAttribute {

    /**
     * 
     */
    private Entity entity;

    /**
     * 
     */
    private AbstractAttribute attribute;

    public FKAttribute(String name, boolean notNull, String defaultValue,
            Entity entity, AbstractAttribute attribute) {
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

    public AbstractAttribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean isNotNull() {
        return true;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setAttribute(AbstractAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public AbstractAttribute copy() {
        return new Attribute(name, getType().copy(), isNotNull(), defaultValue);
    }

}
