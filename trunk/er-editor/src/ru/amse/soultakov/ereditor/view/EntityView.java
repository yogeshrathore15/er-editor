/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author sma
 * 
 */
public class EntityView extends Block {

    protected Entity entity;

    /**
     * @param diagramEditor
     * @param entity
     * @param x
     * @param y
     */
    public EntityView(Entity entity, int x, int y) {
        super(x, y);
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be non-null");
        }
        this.entity = entity;
    }

    public void paint(Graphics2D graphics) {
        Rectangle2D bounds = getContentBounds(graphics);
        setSize((int) bounds.getWidth(), 100);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        graphics.setColor(Color.BLACK);
        drawBorder(graphics);
        drawTitle(graphics);
        int titleHeight = (int) (bounds.getHeight() + MARGIN);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(getX(), getY() + titleHeight, getX() + getWidth(), getY()
                + titleHeight);
        graphics.drawLine(getX(), getY() + (titleHeight + MARGIN) * 2, getX()
                + getWidth(), getY() + (titleHeight + MARGIN) * 2);
        drawSelection(graphics);
    }

    /**
     * @param graphics
     * @return
     */
    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, entity.getName());
        graphics.drawString(entity.getName(), getX(), getY()
                + (int) bounds.getHeight());
        return bounds;
    }

    public Entity getEntity() {
        return entity;
    }

    /**
     * @return
     */
    protected Rectangle2D getContentBounds(Graphics2D graphics) {
        return getStringBounds(graphics, entity.getName());
    }

}
