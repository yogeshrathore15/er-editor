/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics;
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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth() + getInsets().right + getInsets().left, 100);
        drawTitle(graphics);
        int titleHeight = (int) (getInsets().top + bounds.getHeight() + MARGIN);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(getInsets().left, titleHeight, getWidth(), titleHeight);
        graphics.drawLine(getInsets().left, (titleHeight + MARGIN) * 2, getWidth()
                - getInsets().right, (titleHeight + MARGIN) * 2);
        drawSelection(graphics);
    }
    
    /**
     * @param graphics
     * @return
     */
    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, entity.getName());
        graphics.drawString(entity.getName(), getInsets().left,
                (int) (getInsets().top + bounds.getHeight()));
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
