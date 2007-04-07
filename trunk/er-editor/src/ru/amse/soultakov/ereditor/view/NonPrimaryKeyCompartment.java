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
class NonPrimaryKeyCompartment extends AttributesCompartment {

    /**
     * 
     */
    private static final String UNIQUE = "U";

    private static final int ADDITIONAL_COLUMN_WIDTH = 15;

    /**
     * @param x
     * @param entityView
     */
    public NonPrimaryKeyCompartment(int y, EntityView entityView) {
        super(y, entityView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle2D getContentBounds(Graphics2D graphics) {
        Rectangle2D bounds = super.getContentBounds(graphics);
        bounds.setRect(0, 0, bounds.getWidth() + ADDITIONAL_COLUMN_WIDTH, bounds
                .getHeight());
        return bounds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int paint(Graphics2D graphics) {
        paintAdditionalColumn(graphics);
        return super.paint(graphics);
    }

    /**
     * @param graphics
     */
    private void paintAdditionalColumn(Graphics2D graphics) {
        graphics.drawLine(getAdditionalColumnX(), getAbsoluteY(),
                getAdditionalColumnX(), getAbsoluteY() + getHeight(graphics));
        for (AttributeView av : attributes) {
            if (entityView.isUnique(av.getAttribute())) {
                int x = getAdditionalColumnX();
                int y = av.getLastPaintedY();
                graphics.drawString(UNIQUE, x + MARGIN, y);
            }
        }
    }

    private int getAdditionalColumnX() {
        return entityView.getX() + entityView.getWidth() - ADDITIONAL_COLUMN_WIDTH;
    }

}
