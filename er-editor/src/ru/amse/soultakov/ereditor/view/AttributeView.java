package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributeView {

    private AbstractAttribute attribute;

    private EntityView entityView;

    private Compartment compartment;

    private volatile int lastPaintedY;

    public AttributeView(AbstractAttribute attribute, EntityView entityView,
            Compartment compartment) {
        this.attribute = attribute;
        this.entityView = entityView;
        this.compartment = compartment;
    }

    public AbstractAttribute getAttribute() {
        return this.attribute;
    }

    /**
     * @return the compartment
     */
    public Compartment getCompartment() {
        return this.compartment;
    }

    public void setAttribute(AbstractAttribute attribute) {
        this.attribute = attribute;
    }

    public EntityView getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityView entityView) {
        this.entityView = entityView;
    }

    /**
     * @return the lastPaintedY
     */
    public int getLastPaintedY() {
        return lastPaintedY;
    }

    public int paint(Graphics2D graphics, int x, int y) {
        String attrString = getAttributeStringPresentation();
        Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, attrString);
        lastPaintedY = (y + (int) bounds.getHeight());
        graphics.drawString(attrString, Block.MARGIN + entityView.getX(),
                lastPaintedY);
        return lastPaintedY + Block.MARGIN;
    }

    // TODO: добавить возможность менять представление в зависимости от внешних
    // настроек
    public String getAttributeStringPresentation() {
        return this.attribute.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return attribute.getName();
    }

}
