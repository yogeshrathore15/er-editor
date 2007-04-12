/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Soultakov Maxim
 * 
 */
public abstract class Compartment {

    public static final int MARGIN = 3;

    protected int y;

    protected final EntityView entityView;

    public Compartment(int y, EntityView entityView) {
        this.y = y;
        this.entityView = entityView;
    }

    public int getAbsoluteY() {
        return this.y + entityView.getY();
    }

    /**
     * @return the y
     */
    public int getY() {
        return this.y;
    }

    public int getX() {
        return entityView.getX() + MARGIN;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Block getEntityView() {
        return this.entityView;
    }

    public int getHeight(Graphics2D graphics) {
        return (int) getContentBounds(graphics).getHeight();
    }

    public abstract int paint(Graphics2D graphics);

    public abstract Rectangle2D getContentBounds(Graphics2D graphics);

}
