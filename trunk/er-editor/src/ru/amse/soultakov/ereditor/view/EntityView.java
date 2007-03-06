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
        super(x,y);
        this.entity = entity;
    }

    public void paint(Graphics2D graphics) {
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth(), 100);
        drawTitle(graphics);
        int titleHeight = (int) (bounds.getHeight() + MARGIN);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, titleHeight, getWidth(), titleHeight);
        graphics.drawLine(0, (titleHeight + MARGIN) * 2, getWidth(), (titleHeight + MARGIN) * 2);
        drawSelection(graphics);
    }
    
    /**
     * @param graphics
     * @return
     */
    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, entity.getName());
        graphics.drawString(entity.getName(), 0,
                (int) bounds.getHeight());
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
