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

    private static final int ADDITIONAL_COLUMN_WIDTH = 20;

    private static final String FK = "FK";

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
        int curY = super.paint(graphics);
        paintAdditionalColumn(graphics);
        return curY;
    }

    /**
     * @param graphics
     */
    private void paintAdditionalColumn(Graphics2D graphics) {
        graphics.drawLine(getAdditionalColumnX(), getAbsoluteY() - 2,
                getAdditionalColumnX(), getAbsoluteY() + getHeight(graphics)
                        + MARGIN);
        for (AttributeView av : getAttributes()) {
            if (entityView.isUnique(av.getAttribute())) {
                int x = getAdditionalColumnX();
                int y = av.getLastPaintedY();
                graphics.drawString(UNIQUE, x + MARGIN, y);
            }
        }
        for (AttributeView av : getAttributes()) {
            if (entityView.isForeignKey(av.getAttribute())) {
                int x = getAdditionalColumnX();
                int y = av.getLastPaintedY();
                graphics.drawString(FK, x + MARGIN, y);
            }
        }
    }

    private int getAdditionalColumnX() {
        return entityView.getX() + entityView.getWidth() - ADDITIONAL_COLUMN_WIDTH;
    }

}
