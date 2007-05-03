/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class TitleCompartment extends Compartment {

    /**
     * @param x
     * @param y
     * @param entityView
     */
    public TitleCompartment(int y, EntityView entityView, String title) {
        super(y, entityView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int paint(Graphics2D graphics) {
        int height = (int) getContentBounds(graphics).getHeight();
        int newCurY = entityView.getY() + y + height;
        graphics.drawString(entityView.getEntity().getName(), entityView.getX()
                + Block.MARGIN, newCurY);
        return getY() + entityView.getY() + getHeight(graphics);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle2D getContentBounds(Graphics2D graphics) {
        return GraphicsUtils.getStringBounds(graphics, entityView.getEntity()
                .getName());
    }

}
